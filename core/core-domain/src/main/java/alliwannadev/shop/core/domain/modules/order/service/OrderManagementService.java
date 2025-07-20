package alliwannadev.shop.core.domain.modules.order.service;

import alliwannadev.shop.core.domain.modules.order.model.Order;
import alliwannadev.shop.core.domain.modules.order.model.OrderItem;
import alliwannadev.shop.core.domain.modules.order.service.dto.CreateOrderParam;
import alliwannadev.shop.core.domain.modules.order.service.dto.CreateOrderResult;
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

        Order savedOrder = orderService.getOneByOrderNo(createOrderParam.orderNo());
        List<OrderItem> savedOrderItemList = orderService.getOrderItemsById(savedOrder.getOrderId());
        return CreateOrderResult.fromEntity(savedOrder, savedOrderItemList);
    }

    public String generateLockName(String orderNo) {
        return "order:%s".formatted(orderNo);
    }
}
