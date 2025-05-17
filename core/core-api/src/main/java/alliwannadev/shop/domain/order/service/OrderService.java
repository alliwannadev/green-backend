package alliwannadev.shop.domain.order.service;

import alliwannadev.shop.domain.order.model.Order;
import alliwannadev.shop.domain.order.model.OrderItem;
import alliwannadev.shop.domain.order.repository.OrderItemRepository;
import alliwannadev.shop.domain.order.repository.OrderRepository;
import alliwannadev.shop.domain.order.service.dto.CreateOrderParam;
import alliwannadev.shop.domain.order.service.dto.CreateOrderResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    public CreateOrderResult create(CreateOrderParam createOrderParam) {
        Order savedOrder = orderRepository.save(createOrderParam.toOrder());
        List<OrderItem> savedOrderItemList = orderItemRepository.saveAll(createOrderParam.toOrderItemList(savedOrder));

        return CreateOrderResult.fromEntity(savedOrder, savedOrderItemList);
    }
}
