package alliwannadev.shop.core.domain.modules.cart.service.dto;

import alliwannadev.shop.core.domain.modules.cart.model.Cart;
import alliwannadev.shop.core.domain.modules.cart.model.CartItem;

import java.util.List;

public record GetCartItemListResult(
        Long cartId,
        Long totalQuantity,
        Long totalAmount,
        Long totalDiscountedAmount,
        Long totalPaymentAmount,
        List<GetCartItemResult> cartItemList
) {

    public static GetCartItemListResult fromEntities(
            Cart cart,
            List<CartItem> foundCartItems
    ) {
        return new GetCartItemListResult(
                cart.getCartId(),
                cart.getTotalQuantity(),
                cart.getTotalAmount(),
                cart.getTotalDiscountedAmount(),
                cart.getTotalPaymentAmount(),
                foundCartItems.stream()
                        .map(GetCartItemResult::fromEntity)
                        .toList()
        );
    }
}
