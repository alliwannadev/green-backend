package alliwannadev.shop.core.application.modules.cart.service;

import alliwannadev.shop.core.jpa.cart.model.CartEntity;
import alliwannadev.shop.core.application.modules.cart.service.dto.CreateCartItemParam;
import alliwannadev.shop.core.application.modules.cart.service.dto.GetCartItemListResult;
import alliwannadev.shop.core.application.modules.cart.service.dto.UpdateCartItemParam;
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
        CartEntity createdCartEntity = cartService.createIfNotExists(userId);
        return cartItemService.getAllByCartId(createdCartEntity.getCartId());
    }

    @Transactional
    public void addCartItem(
            Long userId,
            CreateCartItemParam createCartItemParam
    ) {
        CartEntity createdCartEntity = cartService.createIfNotExists(userId);
        cartItemService.addOne(
                createdCartEntity.getCartId(),
                createdCartEntity,
                createCartItemParam
        );
        cartService.update(createdCartEntity.getCartId());
    }

    @Transactional
    public void updateCartItem(
            Long userId,
            Long cartItemId,
            UpdateCartItemParam param
    ) {
        CartEntity createdCartEntity = cartService.createIfNotExists(userId);
        cartItemService.update(cartItemId, param);
        cartService.update(createdCartEntity.getCartId());
    }

    @Transactional
    public void deleteCartItem(
            Long userId,
            Long cartItemId
    ) {
        CartEntity createdCartEntity = cartService.createIfNotExists(userId);
        cartItemService.delete(cartItemId);
        cartService.update(createdCartEntity.getCartId());
    }
}
