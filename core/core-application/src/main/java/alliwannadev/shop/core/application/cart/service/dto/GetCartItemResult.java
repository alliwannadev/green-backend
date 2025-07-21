package alliwannadev.shop.core.application.cart.service.dto;

import alliwannadev.shop.core.jpa.cart.model.CartItemEntity;

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
            CartItemEntity cartItemEntity
    ) {
        return new GetCartItemResult(
                cartItemEntity.getCartItemId(),
                cartItemEntity.getProductId(),
                cartItemEntity.getProductName(),
                cartItemEntity.getProductOptionCombinationId(),
                cartItemEntity.getThumbnailUrl(),
                cartItemEntity.getSelectedOptions(),
                cartItemEntity.getQuantity(),
                cartItemEntity.getAmount(),
                cartItemEntity.getDiscountedAmount(),
                cartItemEntity.getPaymentAmount()
        );
    }
}
