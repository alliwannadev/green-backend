package alliwannadev.shop.coupon.eventhandler;

import alliwannadev.shop.support.event.Event;
import alliwannadev.shop.support.event.EventPayload;

public interface EventHandler {

    void handle(Event<? extends EventPayload> event);

    boolean supports(Event<? extends EventPayload> event);
}
