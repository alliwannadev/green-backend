package alliwannadev.shop.core.api.order.controller.dto;

import alliwannadev.shop.core.domain.common.constant.OrderStatus;
import alliwannadev.shop.core.application.order.service.dto.CreateOrderResult;

import java.util.List;

public record CreateOrderResponseV1(
        Long orderId,
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
        List<OrderItemResponse> orderItems
) {

    public record OrderItemResponse(
            Long orderItemId,
            Long productId,
            Long price,
            Long discountedPrice,
            Long paymentPrice,
            Long quantity,
            Long discountedAmount,
            Long paymentAmount
    ) {
    }

    public static CreateOrderResponseV1 fromDto(
            CreateOrderResult createOrderResult
    ) {
        return new CreateOrderResponseV1(
                createOrderResult.orderId(),
                createOrderResult.userId(),
                createOrderResult.orderNo(),
                createOrderResult.orderStatus(),
                createOrderResult.receiverName(),
                createOrderResult.receiverPhoneNumber(),
                createOrderResult.receiverAddr(),
                createOrderResult.receiverPostalCode(),
                createOrderResult.totalQuantity(),
                createOrderResult.totalAmount(),
                createOrderResult.totalDiscountedAmount(),
                createOrderResult.totalPaymentAmount(),
                createOrderResult.orderItems()
                        .stream()
                        .map(orderItem -> new OrderItemResponse(
                                orderItem.orderItemId(),
                                orderItem.productId(),
                                orderItem.price(),
                                orderItem.discountedPrice(),
                                orderItem.paymentPrice(),
                                orderItem.quantity(),
                                orderItem.discountedAmount(),
                                orderItem.paymentAmount()
                        ))
                        .toList()
        );
    }
}
