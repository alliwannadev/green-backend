package alliwannadev.shop.core.application.modules.order.service.dto;

import alliwannadev.shop.core.domain.common.constant.OrderStatus;
import alliwannadev.shop.core.jpa.order.model.Order;
import alliwannadev.shop.core.jpa.order.model.OrderItem;

import java.util.List;

public record CreateOrderResult(
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
        List<OrderItemDto> orderItems
) {

    public record OrderItemDto(
            Long orderItemId,
            Long productId,
            Long productOptionCombinationId,
            Long price,
            Long discountedPrice,
            Long paymentPrice,
            Long quantity,
            Long discountedAmount,
            Long paymentAmount
    ) {
    }

    public static CreateOrderResult fromEntity(
            Order order,
            List<OrderItem> orderItems
    ) {
        return new CreateOrderResult(
                order.getOrderId(),
                order.getUserId(),
                order.getOrderNo(),
                order.getOrderStatus(),
                order.getReceiverName(),
                order.getReceiverPhoneNumber(),
                order.getReceiverAddr(),
                order.getReceiverPostalCode(),
                order.getTotalQuantity(),
                order.getTotalAmount(),
                order.getTotalDiscountedAmount(),
                order.getTotalPaymentAmount(),
                orderItems
                        .stream()
                        .map(orderItem -> new OrderItemDto(
                                orderItem.getOrderItemId(),
                                orderItem.getProductId(),
                                orderItem.getProductOptionCombinationId(),
                                orderItem.getPrice(),
                                orderItem.getDiscountedPrice(),
                                orderItem.getPaymentPrice(),
                                orderItem.getQuantity(),
                                orderItem.getDiscountedAmount(),
                                orderItem.getPaymentAmount()
                        ))
                        .toList()
        );
    }
}
