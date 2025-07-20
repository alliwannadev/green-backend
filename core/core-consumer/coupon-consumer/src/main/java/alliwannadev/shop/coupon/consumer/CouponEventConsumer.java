package alliwannadev.shop.coupon.consumer;

import alliwannadev.shop.coupon.service.CouponEventService;
import alliwannadev.shop.support.event.Event;
import alliwannadev.shop.support.event.EventPayload;
import alliwannadev.shop.support.event.EventType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CouponEventConsumer {

    private final CouponEventService couponEventService;

    @KafkaListener(topics = { EventType.Topic.SHOP_USER })
    public void listen(String message, Acknowledgment ack) {
        log.info("[CouponEventConsumer.listen] 메시지={}", message);

        Event<EventPayload> event = Event.fromJson(message);
        if (event != null) {
            couponEventService.handleEvent(event);
        }

        ack.acknowledge();
    }
}
