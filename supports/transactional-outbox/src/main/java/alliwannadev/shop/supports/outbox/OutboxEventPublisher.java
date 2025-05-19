package alliwannadev.shop.supports.outbox;

import alliwannadev.shop.supports.event.Event;
import alliwannadev.shop.supports.event.EventPayload;
import alliwannadev.shop.supports.event.EventType;
import alliwannadev.shop.supports.snowflake.Snowflake;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class OutboxEventPublisher {

    private final Snowflake snowflake;
    private final ApplicationEventPublisher applicationEventPublisher;

    public void publish(
            EventType type,
            EventPayload payload,
            Long shardKey
    ) {
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

        applicationEventPublisher.publishEvent(OutboxEvent.of(outbox));
    }
}
