package alliwannadev.shop.domain.order.service.dto;

import alliwannadev.shop.common.constant.OrderStatus;
import alliwannadev.shop.domain.order.model.Order;
import alliwannadev.shop.domain.order.model.OrderItem;

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

    public List<OrderItem> toOrderItemList(
            Order order
    ) {
        return orderItemDtoList
                .stream()
                .map(orderItemDto ->
                        OrderItem.of(
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

    public Order toOrder() {
        return Order.of(
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
