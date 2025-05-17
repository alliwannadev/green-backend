package alliwannadev.shop.domain.cart.service.dto;

public record UpdateCartItemParam(
        Long productId,
        String productName,
        Long quantity,
        Long price,
        Long amount,
        Long discountedAmount
) {
}
