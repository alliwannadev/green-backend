package alliwannadev.shop.domain.cart.service.dto;

import alliwannadev.shop.domain.cart.model.CartItem;

public record GetCartItemResult(
        Long cartItemId,
        Long productId,
        String productName,
        Long productOptionCombinationId,
        String selectedOptions,
        String thumbnailUrl,
        Long quantity,
        Long amount,
        Long discountedAmount,
        Long paymentAmount
) {
    public static GetCartItemResult fromEntity(
            CartItem cartItem
    ) {
        return new GetCartItemResult(
                cartItem.getCartItemId(),
                cartItem.getProductId(),
                cartItem.getProductName(),
                cartItem.getProductOptionCombinationId(),
                cartItem.getThumbnailUrl(),
                cartItem.getSelectedOptions(),
                cartItem.getQuantity(),
                cartItem.getAmount(),
                cartItem.getDiscountedAmount(),
                cartItem.getPaymentAmount()
        );
    }
}
