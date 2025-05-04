package alliwannadev.shop.supports.outbox;

import alliwannadev.shop.supports.event.EventType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@Getter
@Table(name = "outbox")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Outbox {

    @Id
    private Long outboxId;

    private Long shardKey;

    @Enumerated(EnumType.STRING)
    private EventType eventType;

    private String payload;

    private LocalDateTime createdAt;

    public static Outbox create(
            Long outboxId,
            Long shardKey,
            EventType eventType,
            String payload
    ) {
        Outbox outbox = new Outbox();
        outbox.outboxId = outboxId;
        outbox.shardKey = shardKey;
        outbox.eventType = eventType;
        outbox.payload = payload;
        outbox.createdAt = LocalDateTime.now();

        return outbox;
    }
}
