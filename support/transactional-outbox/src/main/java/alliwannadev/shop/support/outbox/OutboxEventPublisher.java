package alliwannadev.shop.support.outbox;

import alliwannadev.shop.support.event.Event;
import alliwannadev.shop.support.event.EventPayload;
import alliwannadev.shop.support.event.EventType;
import alliwannadev.shop.support.snowflake.Snowflake;
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
