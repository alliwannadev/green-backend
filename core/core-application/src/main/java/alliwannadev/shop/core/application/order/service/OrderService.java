package alliwannadev.shop.core.application.order.service;

import alliwannadev.shop.core.jpa.order.model.OrderEntity;
import alliwannadev.shop.core.jpa.order.model.OrderItemEntity;
import alliwannadev.shop.core.jpa.order.repository.OrderItemJpaRepository;
import alliwannadev.shop.core.jpa.order.repository.OrderJpaRepository;
import alliwannadev.shop.core.application.order.service.dto.CreateOrderParam;
import alliwannadev.shop.core.application.order.service.dto.CreateOrderResult;
import alliwannadev.shop.core.jpa.stock.model.StockEntity;
import alliwannadev.shop.core.application.stock.service.StockService;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class OrderService {

    private final StockService stockService;

    private final OrderJpaRepository orderJpaRepository;
    private final OrderItemJpaRepository orderItemJpaRepository;

    @Transactional
    public CreateOrderResult create(CreateOrderParam createOrderParam) {
        OrderEntity savedOrder = orderJpaRepository.save(createOrderParam.toOrder());
        List<OrderItemEntity> savedOrderItemList = orderItemJpaRepository.saveAll(createOrderParam.toOrderItemList(savedOrder));
        savedOrderItemList
                .forEach(
                        orderItem -> {
                            StockEntity stock =
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
    public OrderEntity getOneByOrderNo(String orderNo) {
        return orderJpaRepository
                        .findByOrderNo(orderNo)
                        .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
    }

    @Transactional
    public List<OrderItemEntity> getOrderItemsById(Long orderId) {
        return orderItemJpaRepository.findAllByOrderId(orderId);
    }
}
