package alliwannadev.shop.domain.cart.controller.dto.request;

import alliwannadev.shop.core.domain.modules.cart.service.dto.UpdateCartItemParam;

public record UpdateCartItemRequestV1(
        Long productId,
        Long quantity,
        Long price,
        Long amount,
        Long discountedAmount
) {

    public UpdateCartItemParam toParam() {
        return new UpdateCartItemParam(
                productId,
                quantity,
                price,
                amount,
                discountedAmount
        );
    }
}
