package alliwannadev.shop.domain.cart.service.dto;

import alliwannadev.shop.domain.cart.model.Cart;
import alliwannadev.shop.domain.cart.model.CartItem;

public record CreateCartItemParam(
        Long productId,
        String productName,
        String thumbnailUrl,
        Long price,
        Long quantity,
        Long amount,
        Long discountedAmount
) {

    public CartItem toEntity(
            Cart cart
    ) {
        return CartItem.of(
                cart,
                productId,
                productName,
                thumbnailUrl,
                price,
                quantity,
                amount,
                discountedAmount
        );
    }
}
