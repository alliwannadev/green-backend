package alliwannadev.shop.core.domain.modules.cart.service.dto;

import alliwannadev.shop.core.domain.modules.cart.model.Cart;
import alliwannadev.shop.core.domain.modules.cart.model.CartItem;

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

    public CartItem toEntity(
            Cart cart,
            Long productOptionCombinationId
    ) {
        return CartItem.of(
                cart,
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
