package alliwannadev.shop.api.order.controller.dto;

import alliwannadev.shop.core.domain.common.constant.OrderStatus;
import alliwannadev.shop.core.application.common.util.OrderNoUtil;
import alliwannadev.shop.core.application.modules.order.service.dto.CreateOrderParam;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;

import java.util.List;

public record CreateOrderRequestV1(
        @NotBlank String receiverName,
        @NotBlank String receiverPhoneNumber,
        @NotBlank String receiverAddr,
        @NotBlank String receiverPostalCode,
        @Positive Long totalQuantity,
        @Positive Long totalAmount,
        @Min(value = 0) Long totalDiscountedAmount,
        @Positive Long totalPaymentAmount,
        @Valid @NotEmpty List<OrderItem> orderItemList
) {

    public record OrderItem(
            @Positive Long productId,
            @Positive Long productOptionCombinationId,
            @Positive Long price,
            @Min(value = 0) Long discountedPrice,
            @Positive Long paymentPrice,
            @Positive Long quantity,
            @Positive Long amount,
            @Min(value = 0) Long discountedAmount,
            @Positive Long paymentAmount
    ) {
    }

    public CreateOrderParam toDto(Long userId) {
        return new CreateOrderParam(
                userId,
                OrderNoUtil.createOrderNo(),
                OrderStatus.ORDER_REQUEST,
                receiverName,
                receiverPhoneNumber,
                receiverAddr,
                receiverPostalCode,
                totalQuantity,
                totalAmount,
                totalDiscountedAmount,
                totalPaymentAmount,
                orderItemList
                        .stream()
                        .map(orderItem ->
                                new CreateOrderParam.OrderItemDto(
                                        orderItem.productId,
                                        orderItem.productOptionCombinationId,
                                        orderItem.price,
                                        orderItem.discountedPrice,
                                        orderItem.paymentPrice,
                                        orderItem.quantity,
                                        orderItem.amount,
                                        orderItem.discountedAmount,
                                        orderItem.paymentAmount
                                )
                        )
                        .toList()
        );
    }
}
