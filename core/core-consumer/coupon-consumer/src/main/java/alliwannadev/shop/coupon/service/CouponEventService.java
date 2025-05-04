package alliwannadev.shop.coupon.service;

import alliwannadev.shop.coupon.eventhandler.EventHandler;
import alliwannadev.shop.supports.event.Event;
import alliwannadev.shop.supports.event.EventPayload;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class CouponEventService {

    private final List<EventHandler<EventPayload>> eventHandlers;

    public void handleEvent(Event<EventPayload> event) {
        EventHandler<EventPayload> eventHandler = findEventHandler(event);
        if (eventHandler == null) {
            return;
        }

        eventHandler.handle(event);
    }

    private EventHandler<EventPayload> findEventHandler(Event<EventPayload> event) {
        return eventHandlers
                .stream()
                .filter(eventHandler -> eventHandler.supports(event))
                .findAny()
                .orElse(null);
    }
}
