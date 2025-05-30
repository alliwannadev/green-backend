package alliwannadev.shop.coupon.eventhandler;

import alliwannadev.shop.core.domain.modules.coupon.service.CouponService;
import alliwannadev.shop.core.domain.modules.coupon.service.dto.IssueCouponParam;
import alliwannadev.shop.supports.event.Event;
import alliwannadev.shop.supports.event.EventPayload;
import alliwannadev.shop.supports.event.EventType;
import alliwannadev.shop.supports.event.payload.UserSignedUpEventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserSignedUpEventHandler implements EventHandler {

    private static final String WELCOME_COUPON_NAME = "WELCOME COUPON";

    private final CouponService couponService;

    @Override
    public void handle(Event<? extends EventPayload> event) {
        UserSignedUpEventPayload payload = (UserSignedUpEventPayload) event.getPayload();
        couponService.issueCoupon(
                new IssueCouponParam(
                        payload.getUserId(),
                        WELCOME_COUPON_NAME
                )
        );
    }

    @Override
    public boolean supports(Event<? extends EventPayload> event) {
        return EventType.USER_SIGNED_UP == event.getEventType();
    }
}
