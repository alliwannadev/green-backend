package alliwannadev.shop.domain.cart.controller.dto.request;

import alliwannadev.shop.domain.cart.service.dto.UpdateCartItemParam;

public record UpdateCartItemRequestV1(
        Long productId,
        String productName,
        Long quantity,
        Long price,
        Long amount,
        Long discountedAmount
) {

    public UpdateCartItemParam toParam() {
        return new UpdateCartItemParam(
                productId,
                productName,
                quantity,
                price,
                amount,
                discountedAmount
        );
    }
}
