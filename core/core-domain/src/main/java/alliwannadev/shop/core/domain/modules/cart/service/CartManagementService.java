package alliwannadev.shop.core.domain.modules.cart.service;

import alliwannadev.shop.core.domain.modules.cart.model.Cart;
import alliwannadev.shop.core.domain.modules.cart.service.dto.CreateCartItemParam;
import alliwannadev.shop.core.domain.modules.cart.service.dto.GetCartItemListResult;
import alliwannadev.shop.core.domain.modules.cart.service.dto.UpdateCartItemParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class CartManagementService {

    private final CartService cartService;
    private final CartItemService cartItemService;

    @Transactional
    public GetCartItemListResult getCartItemList(Long userId) {
        Cart createdCart = cartService.createIfNotExists(userId);
        return cartItemService.getAllByCartId(createdCart.getCartId());
    }

    @Transactional
    public void addCartItem(
            Long userId,
            CreateCartItemParam createCartItemParam
    ) {
        Cart createdCart = cartService.createIfNotExists(userId);
        cartItemService.addOne(
                createdCart.getCartId(),
                createdCart,
                createCartItemParam
        );
        cartService.update(createdCart.getCartId());
    }

    @Transactional
    public void updateCartItem(
            Long userId,
            Long cartItemId,
            UpdateCartItemParam param
    ) {
        Cart createdCart = cartService.createIfNotExists(userId);
        cartItemService.update(cartItemId, param);
        cartService.update(createdCart.getCartId());
    }

    @Transactional
    public void deleteCartItem(
            Long userId,
            Long cartItemId
    ) {
        Cart createdCart = cartService.createIfNotExists(userId);
        cartItemService.delete(cartItemId);
        cartService.update(createdCart.getCartId());
    }
}
