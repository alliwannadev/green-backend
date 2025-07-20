package alliwannadev.shop.scheduler.common;

import alliwannadev.shop.support.outbox.KafkaEventSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@Component
public class MessageRelay {

    private final KafkaEventSender kafkaEventSender;

    @Scheduled(
            fixedDelay = 10,
            initialDelay = 5,
            timeUnit = TimeUnit.SECONDS,
            scheduler = "messageRelayExecutor"
    )
    public void publishPendingEvent() {
        kafkaEventSender.publishPendingEvent();
    }
}
