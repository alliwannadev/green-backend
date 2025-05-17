package alliwannadev.shop.domain.cart.service.dto;

public record UpdateCartItemParam(
        Long productId,
        Long quantity,
        Long price,
        Long amount,
        Long discountedAmount
) {
}
