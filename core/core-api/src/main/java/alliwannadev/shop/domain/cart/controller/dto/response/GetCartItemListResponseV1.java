package alliwannadev.shop.domain.cart.controller.dto.response;

import alliwannadev.shop.core.domain.modules.cart.service.dto.GetCartItemListResult;

import java.util.List;

public record GetCartItemListResponseV1(
        Long cartId,
        Long totalQuantity,
        Long totalAmount,
        Long totalDiscountedAmount,
        Long totalPaymentAmount,
        List<GetCartItemResponseV1> cartItemList
) {

    public static GetCartItemListResponseV1 fromDto(
            GetCartItemListResult cartItemListResult
    ) {
        return new GetCartItemListResponseV1(
                cartItemListResult.cartId(),
                cartItemListResult.totalQuantity(),
                cartItemListResult.totalAmount(),
                cartItemListResult.totalDiscountedAmount(),
                cartItemListResult.totalPaymentAmount(),
                cartItemListResult.cartItemList().stream()
                        .map(GetCartItemResponseV1::fromDto)
                        .toList()
        );
    }
}
