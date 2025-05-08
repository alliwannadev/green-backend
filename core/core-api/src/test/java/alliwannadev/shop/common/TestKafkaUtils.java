package alliwannadev.shop.common;

import alliwannadev.shop.supports.event.Event;
import alliwannadev.shop.supports.event.EventPayload;
import alliwannadev.shop.supports.event.EventType;
import alliwannadev.shop.supports.outbox.MessageRelay;
import alliwannadev.shop.supports.outbox.Outbox;
import alliwannadev.shop.supports.outbox.OutboxEvent;
import alliwannadev.shop.supports.outbox.OutboxEventPublisher;
import alliwannadev.shop.supports.snowflake.Snowflake;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.awaitility.Awaitility;
import org.mockito.BDDMockito;

import java.time.Duration;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static alliwannadev.shop.common.TestContainers.KAFKA_CONTAINER;
import static org.mockito.ArgumentMatchers.any;

@Slf4j
public class TestKafkaUtils {

    public static void createTopic(
            String topicName,
            int numPartitions,
            short replicationFactor
    ) {
        // AdminClient 생성
        Properties properties = new Properties();
        properties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_CONTAINER.getBootstrapServers());
        AdminClient adminClient = AdminClient.create(properties);

        // 토픽 속성 정의
        NewTopic newTopic = new NewTopic(topicName, numPartitions, replicationFactor);

        // 토픽 생성
        try {
            adminClient.createTopics(Collections.singletonList(newTopic)).all().get();
        } catch (Exception e) {
            log.error("[TestKafkaUtils.createTopic] {}", e.getMessage(), e);
        }
    }

    public static KafkaConsumer<String, String> createConsumer(
            String consumerGroupId
    ) {

        return new KafkaConsumer<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                        KAFKA_CONTAINER.getBootstrapServers(),
                        ConsumerConfig.GROUP_ID_CONFIG,
                        consumerGroupId,
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG,
                        "earliest"
                ),
                new StringDeserializer(),
                new StringDeserializer());
    }

    public static List<ConsumerRecord<String, String>> getAllRecords(
            KafkaConsumer<String, String> consumer,
            int expectedRecordCount
    ) {
        List<ConsumerRecord<String, String>> allRecords = new ArrayList<>();
        Awaitility.await()
                .atMost(10, TimeUnit.SECONDS)
                .until(
                        () -> {
                            consumer.poll(Duration.ofMillis(50))
                                    .iterator()
                                    .forEachRemaining(allRecords::add);

                            return allRecords.size() == expectedRecordCount;
                        });

        return allRecords;
    }

    public static List<ConsumerRecord<String, String>> createConsumerAndGetAllRecords(
            String consumerGroupId,
            int expectedRecordCount
    ) {
        KafkaConsumer<String, String> couponConsumer = createConsumer(consumerGroupId);
        couponConsumer.subscribe(List.of(EventType.USER_SIGNED_UP.getTopic()));
        return getAllRecords(couponConsumer, expectedRecordCount);
    }

    public static void mockPublishingOutboxEvent(
            OutboxEventPublisher mockBeanOfOutboxEventPublisher,
            MessageRelay messageRelay
    ) {
        BDDMockito.will(invocation -> {
                    Snowflake snowflake = new Snowflake(1L, 1L);
                    EventType type = invocation.getArgument(0);
                    EventPayload payload = invocation.getArgument(1);
                    Long shardKey = invocation.getArgument(2);

                    Outbox outbox = Outbox.create(
                            snowflake.nextId(),
                            shardKey,
                            type,
                            Event.of(
                                    snowflake.nextId(),
                                    shardKey,
                                    type,
                                    payload
                            ).toJson()
                    );
                    messageRelay.publishEvent(OutboxEvent.of(outbox));

                    return null;
                }).given(mockBeanOfOutboxEventPublisher)
                .publish(any(EventType.class), any(), any());
    }
}
