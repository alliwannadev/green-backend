package alliwannadev.shop.core.application.order.service;

import alliwannadev.shop.core.jpa.order.model.OrderEntity;
import alliwannadev.shop.core.jpa.order.model.OrderItemEntity;
import alliwannadev.shop.core.application.order.service.dto.CreateOrderParam;
import alliwannadev.shop.core.application.order.service.dto.CreateOrderResult;
import alliwannadev.shop.support.distributedlock.DistributedLockExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderManagementService {

    private final DistributedLockExecutor distributedLockExecutor;
    private final OrderService orderService;

    public CreateOrderResult createOrder(CreateOrderParam createOrderParam) {
        distributedLockExecutor.execute(
                generateLockName(createOrderParam.orderNo()),
                3000L,
                3000L,
                () -> orderService.create(createOrderParam)
        );

        OrderEntity savedOrder = orderService.getOneByOrderNo(createOrderParam.orderNo());
        List<OrderItemEntity> savedOrderItemList = orderService.getOrderItemsById(savedOrder.getOrderId());
        return CreateOrderResult.fromEntity(savedOrder, savedOrderItemList);
    }

    public String generateLockName(String orderNo) {
        return "order:%s".formatted(orderNo);
    }
}
