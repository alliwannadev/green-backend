package alliwannadev.shop.domain.cart.service;

import alliwannadev.shop.common.error.BusinessException;
import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.domain.cart.model.Cart;
import alliwannadev.shop.domain.cart.model.CartItem;
import alliwannadev.shop.domain.cart.repository.CartItemRepository;
import alliwannadev.shop.domain.cart.repository.CartRepository;
import alliwannadev.shop.domain.cart.service.dto.CreateCartItemParam;
import alliwannadev.shop.domain.cart.service.dto.GetCartItemListResult;
import alliwannadev.shop.domain.cart.service.dto.UpdateCartItemParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartItemService {

    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional(readOnly = true)
    public GetCartItemListResult getAllByCartId(Long cartId) {
        Cart foundCart = cartRepository
                .findById(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));
        List<CartItem> foundCartItems = cartItemRepository.findAllByCartId(cartId);

        return GetCartItemListResult.fromEntities(
                foundCart,
                foundCartItems
        );
    }

    @Transactional
    public void addOne(
            Long cartId,
            Cart cart,
            CreateCartItemParam createCartItemParam
    ) {
        CartItem newCartItem = createCartItemParam.toEntity(cart);
        cartItemRepository
                .findOne(
                        cartId,
                        newCartItem.getProductId(),
                        newCartItem.getSelectedOptions()
                )
                .ifPresentOrElse(
                        cartItem -> cartItem.updateQuantity(newCartItem.getQuantity()),
                        () -> cartItemRepository.save(newCartItem)
                );
    }

    @Transactional
    public void update(
            Long cartItemId,
            UpdateCartItemParam param
    ) {
        CartItem foundCartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));
        foundCartItem.update(
                param.price(),
                param.quantity(),
                param.amount(),
                param.discountedAmount()
        );
    }

    @Transactional
    public void delete(
            Long cartItemId
    ) {
        CartItem foundCartItem = cartItemRepository
                .findById(cartItemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));
        cartItemRepository.delete(foundCartItem);
    }
}
