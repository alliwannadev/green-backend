package alliwannadev.shop.core.application.cart.service.dto;

import alliwannadev.shop.core.jpa.cart.model.CartEntity;
import alliwannadev.shop.core.jpa.cart.model.CartItemEntity;

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
            CartEntity cartEntity,
            List<CartItemEntity> foundCartItemEntities
    ) {
        return new GetCartItemListResult(
                cartEntity.getCartId(),
                cartEntity.getTotalQuantity(),
                cartEntity.getTotalAmount(),
                cartEntity.getTotalDiscountedAmount(),
                cartEntity.getTotalPaymentAmount(),
                foundCartItemEntities.stream()
                        .map(GetCartItemResult::fromEntity)
                        .toList()
        );
    }
}
