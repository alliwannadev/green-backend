package alliwannadev.shop.core.application.modules.cart.service.dto;

import alliwannadev.shop.core.jpa.cart.model.CartEntity;
import alliwannadev.shop.core.jpa.cart.model.CartItemEntity;

public record CreateCartItemParam(
        Long productId,
        String productName,
        String selectedOptions,
        String thumbnailUrl,
        Long price,
        Long quantity,
        Long amount,
        Long discountedAmount
) {

    public CartItemEntity toEntity(
            CartEntity cartEntity,
            Long productOptionCombinationId
    ) {
        return CartItemEntity.of(
                cartEntity,
                productId,
                productName,
                productOptionCombinationId,
                selectedOptions,
                thumbnailUrl,
                price,
                quantity,
                amount,
                discountedAmount
        );
    }
}
