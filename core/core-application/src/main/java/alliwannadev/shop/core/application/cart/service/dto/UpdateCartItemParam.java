package alliwannadev.shop.core.application.cart.service.dto;

public record UpdateCartItemParam(
        Long productId,
        Long quantity,
        Long price,
        Long amount,
        Long discountedAmount
) {
}
