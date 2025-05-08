package alliwannadev.shop.supports.event;

import alliwannadev.shop.supports.dataserializer.DataSerializer;
import lombok.Getter;

@Getter
public class Event<T extends EventPayload>  {

    private Long eventId;
    private Long shardKey;
    private EventType eventType;
    private T payload;

    public static Event<EventPayload> of(
            Long eventId,
            Long shardKey,
            EventType eventType,
            EventPayload payload
    ) {
        Event<EventPayload> event = new Event<>();
        event.eventId = eventId;
        event.shardKey = shardKey;
        event.eventType = eventType;
        event.payload = payload;

        return event;
    }

    public String toJson() {
        return DataSerializer.serialize(this);
    }

    public static Event<EventPayload> fromJson(String json) {
        RawEvent rawEvent = DataSerializer.deserialize(json, RawEvent.class);
        if (rawEvent == null) {
            return null;
        }

        Event<EventPayload> event = new Event<>();
        event.eventId = rawEvent.getEventId();
        event.shardKey = rawEvent.getShardKey();
        event.eventType = EventType.from(rawEvent.getEventType());
        event.payload =
                DataSerializer.deserialize(
                        rawEvent.getPayload(),
                        event.eventType.getPayloadClass()
                );

        return event;
    }

    @Getter
    private static class RawEvent {
        private Long eventId;
        private Long shardKey;
        private String eventType;
        private Object payload;
    }
}
