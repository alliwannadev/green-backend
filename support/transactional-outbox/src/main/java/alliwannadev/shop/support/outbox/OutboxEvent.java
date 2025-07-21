package alliwannadev.shop.support.outbox;

import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class OutboxEvent {

    private Outbox outbox;

    public static OutboxEvent of(Outbox outbox) {
        OutboxEvent outboxEvent = new OutboxEvent();
        outboxEvent.outbox = outbox;
        return outboxEvent;
    }
}
