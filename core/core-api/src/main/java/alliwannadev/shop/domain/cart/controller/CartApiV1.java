package alliwannadev.shop.domain.cart.controller;

import alliwannadev.shop.common.dto.OkResponse;
import alliwannadev.shop.core.domain.modules.auth.CustomUser;
import alliwannadev.shop.domain.cart.controller.dto.request.CreateCartItemRequestV1;
import alliwannadev.shop.domain.cart.controller.dto.request.UpdateCartItemRequestV1;
import alliwannadev.shop.domain.cart.controller.dto.response.GetCartItemListResponseV1;
import alliwannadev.shop.core.domain.modules.cart.service.CartManagementService;
import alliwannadev.shop.core.domain.modules.cart.service.dto.GetCartItemListResult;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class CartApiV1 {

    public static final String V1 = "/v1";
    public static final String V1_CART_ITEMS = V1 + "/cart-items";
    public static final String V1_CART_ITEMS_BY_ID = V1_CART_ITEMS + "/{cartItemId}";

    private final CartManagementService cartManagementService;

    @GetMapping(V1_CART_ITEMS)
    public OkResponse<GetCartItemListResponseV1> getCartItemList(
            @AuthenticationPrincipal CustomUser customUser
    ) {
        GetCartItemListResult cartItemList = cartManagementService.getCartItemList(customUser.getUserId());
        return OkResponse.of(GetCartItemListResponseV1.fromDto(cartItemList));
    }

    @PostMapping(V1_CART_ITEMS)
    public OkResponse<Void> addCartItem(
            @AuthenticationPrincipal CustomUser customUser,
            @RequestBody CreateCartItemRequestV1 request
    ) {
        cartManagementService.addCartItem(customUser.getUserId(), request.toParam());
        return OkResponse.of("장바구니에 새로운 항목을 추가하였습니다.");
    }

    @PutMapping(V1_CART_ITEMS_BY_ID)
    public OkResponse<Void> update(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable Long cartItemId,
            @RequestBody UpdateCartItemRequestV1 request
    ) {
        cartManagementService.updateCartItem(
                customUser.getUserId(),
                cartItemId,
                request.toParam()
        );
        return OkResponse.of("장바구니에 담긴 항목을 변경하였습니다.");
    }

    @DeleteMapping(V1_CART_ITEMS_BY_ID)
    public OkResponse<Void> delete(
            @AuthenticationPrincipal CustomUser customUser,
            @PathVariable Long cartItemId
    ) {
        cartManagementService.deleteCartItem(
                customUser.getUserId(),
                cartItemId
        );
        return OkResponse.of("장바구니에 담긴 항목을 삭제하였습니다.");
    }
}
