package alliwannadev.shop.coupon.eventhandler;

import alliwannadev.shop.supports.event.Event;
import alliwannadev.shop.supports.event.EventType;
import alliwannadev.shop.supports.event.payload.UserSignedUpEventPayload;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class UserSignedUpEventHandler implements EventHandler<UserSignedUpEventPayload> {

    @Override
    public void handle(Event<UserSignedUpEventPayload> event) {
        UserSignedUpEventPayload payload = event.getPayload();

        // TODO: 작업 처리 (Welcome Coupon 발급 로직)
    }

    @Override
    public boolean supports(Event<UserSignedUpEventPayload> event) {
        return EventType.USER_SIGNED_UP == event.getEventType();
    }
}
