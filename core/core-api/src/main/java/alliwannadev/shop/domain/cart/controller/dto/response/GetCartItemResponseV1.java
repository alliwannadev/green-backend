package alliwannadev.shop.domain.cart.controller.dto.response;

import alliwannadev.shop.core.domain.modules.cart.service.dto.GetCartItemResult;

public record GetCartItemResponseV1(
        Long cartItemId,
        Long productId,
        String productName,
        Long productOptionCombinationId,
        String thumbnailUrl,
        Long quantity,
        Long amount,
        Long discountedAmount,
        Long paymentAmount
) {
    public static GetCartItemResponseV1 fromDto(
            GetCartItemResult cartItem
    ) {
        return new GetCartItemResponseV1(
                cartItem.cartItemId(),
                cartItem.productId(),
                cartItem.productName(),
                cartItem.productOptionCombinationId(),
                cartItem.thumbnailUrl(),
                cartItem.quantity(),
                cartItem.amount(),
                cartItem.discountedAmount(),
                cartItem.paymentAmount()
        );
    }
}
