package alliwannadev.shop.core.domain.modules.order.service;

import alliwannadev.shop.core.domain.common.error.BusinessException;
import alliwannadev.shop.core.domain.common.error.ErrorCode;
import alliwannadev.shop.core.domain.modules.order.model.Order;
import alliwannadev.shop.core.domain.modules.order.model.OrderItem;
import alliwannadev.shop.core.domain.modules.order.repository.OrderItemRepository;
import alliwannadev.shop.core.domain.modules.order.repository.OrderRepository;
import alliwannadev.shop.core.domain.modules.order.service.dto.CreateOrderParam;
import alliwannadev.shop.core.domain.modules.order.service.dto.CreateOrderResult;
import alliwannadev.shop.core.domain.modules.stock.model.Stock;
import alliwannadev.shop.core.domain.modules.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final StockService stockService;

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;

    @Transactional
    public CreateOrderResult create(CreateOrderParam createOrderParam) {
        Order savedOrder = orderRepository.save(createOrderParam.toOrder());
        List<OrderItem> savedOrderItemList = orderItemRepository.saveAll(createOrderParam.toOrderItemList(savedOrder));
        savedOrderItemList
                .forEach(
                        orderItem -> {
                            Stock stock =
                                    stockService
                                            .getOneByCombinationId(orderItem.getProductOptionCombinationId())
                                            .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));
                            stockService.decreaseQuantity(
                                    stock.getStockId(),
                                    orderItem.getQuantity()
                            );
                        }
                );

        return CreateOrderResult.fromEntity(savedOrder, savedOrderItemList);
    }

    @Transactional(readOnly = true)
    public Order getOneByOrderNo(String orderNo) {
        return orderRepository
                        .findByOrderNo(orderNo)
                        .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
    }

    @Transactional
    public List<OrderItem> getOrderItemsById(Long orderId) {
        return orderItemRepository.findAllByOrderId(orderId);
    }
}
