package alliwannadev.shop.domain.order.service;

import alliwannadev.shop.common.error.BusinessException;
import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.domain.order.model.Order;
import alliwannadev.shop.domain.order.model.OrderItem;
import alliwannadev.shop.domain.order.repository.OrderItemRepository;
import alliwannadev.shop.domain.order.repository.OrderRepository;
import alliwannadev.shop.domain.order.service.dto.CreateOrderParam;
import alliwannadev.shop.domain.order.service.dto.CreateOrderResult;
import alliwannadev.shop.domain.stock.model.Stock;
import alliwannadev.shop.domain.stock.service.StockService;
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
