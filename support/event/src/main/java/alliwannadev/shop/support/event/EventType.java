package alliwannadev.shop.support.event;

import alliwannadev.shop.support.event.payload.UserSignedUpEventPayload;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {

    USER_SIGNED_UP(UserSignedUpEventPayload.class, Topic.SHOP_USER);

    private final Class<? extends EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type) {
        try {
            return valueOf(type);
        } catch (Exception e) {
            log.error("[EventType.from] type={}", type, e);
            throw new IllegalArgumentException(e);
        }
    }

    public static class Topic {
        public static final String SHOP_USER = "shop-user";
    }
}
