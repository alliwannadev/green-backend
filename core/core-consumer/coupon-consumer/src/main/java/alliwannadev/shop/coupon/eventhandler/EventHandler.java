package alliwannadev.shop.coupon.eventhandler;

import alliwannadev.shop.supports.event.Event;
import alliwannadev.shop.supports.event.EventPayload;

public interface EventHandler {

    void handle(Event<? extends EventPayload> event);

    boolean supports(Event<? extends EventPayload> event);
}
