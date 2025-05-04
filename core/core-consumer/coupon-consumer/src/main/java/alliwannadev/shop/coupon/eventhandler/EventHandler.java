package alliwannadev.shop.coupon.eventhandler;

import alliwannadev.shop.supports.event.Event;
import alliwannadev.shop.supports.event.EventPayload;

public interface EventHandler<T extends EventPayload> {

    void handle(Event<T> event);

    boolean supports(Event<T> event);
}
