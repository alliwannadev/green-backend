package alliwannadev.shop.api.cart.controller.dto.request;

import alliwannadev.shop.core.application.modules.cart.service.dto.CreateCartItemParam;

public record CreateCartItemRequestV1(
        Long productId,
        String productName,
        String selectedOptions,
        String thumbnailUrl,
        Long price,
        Long quantity,
        Long amount,
        Long discountedAmount
) {

    public CreateCartItemParam toParam() {
        return new CreateCartItemParam(
                productId,
                productName,
                selectedOptions,
                thumbnailUrl,
                price,
                quantity,
                amount,
                discountedAmount
        );
    }
}
