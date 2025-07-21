package alliwannadev.shop.core.application.order.service.dto;

import alliwannadev.shop.core.domain.common.constant.OrderStatus;
import alliwannadev.shop.core.jpa.order.model.OrderEntity;
import alliwannadev.shop.core.jpa.order.model.OrderItemEntity;

import java.util.List;

public record CreateOrderParam(
        Long userId,
        String orderNo,
        OrderStatus orderStatus,
        String receiverName,
        String receiverPhoneNumber,
        String receiverAddr,
        String receiverPostalCode,
        Long totalQuantity,
        Long totalAmount,
        Long totalDiscountedAmount,
        Long totalPaymentAmount,
        List<OrderItemDto> orderItemDtoList
) {

    public record OrderItemDto(
            Long productId,
            Long productOptionCombinationId,
            Long price,
            Long discountedPrice,
            Long paymentPrice,
            Long quantity,
            Long amount,
            Long discountedAmount,
            Long paymentAmount
    ) {
    }

    public List<OrderItemEntity> toOrderItemList(
            OrderEntity order
    ) {
        return orderItemDtoList
                .stream()
                .map(orderItemDto ->
                        OrderItemEntity.of(
                                order,
                                orderItemDto.productId,
                                orderItemDto.productOptionCombinationId,
                                orderItemDto.price,
                                orderItemDto.discountedPrice,
                                orderItemDto.paymentPrice,
                                orderItemDto.quantity,
                                orderItemDto.amount,
                                orderItemDto.discountedAmount,
                                orderItemDto.paymentAmount
                        )
                )
                .toList();
    }

    public OrderEntity toOrder() {
        return OrderEntity.of(
                userId,
                orderNo,
                orderStatus,
                receiverName,
                receiverPhoneNumber,
                receiverAddr,
                receiverPostalCode,
                totalQuantity,
                totalAmount,
                totalDiscountedAmount,
                totalPaymentAmount
        );
    }
}
