package alliwannadev.shop.core.domain.modules.cart.service;

import alliwannadev.shop.core.domain.modules.cart.model.Cart;
import alliwannadev.shop.core.domain.modules.cart.model.CartItem;
import alliwannadev.shop.core.domain.modules.cart.repository.CartItemRepository;
import alliwannadev.shop.core.domain.modules.cart.repository.CartRepository;
import alliwannadev.shop.core.domain.modules.cart.service.dto.CreateCartItemParam;
import alliwannadev.shop.core.domain.modules.cart.service.dto.GetCartItemListResult;
import alliwannadev.shop.core.domain.modules.cart.service.dto.UpdateCartItemParam;
import alliwannadev.shop.core.domain.common.error.BusinessException;
import alliwannadev.shop.core.domain.common.error.ErrorCode;
import alliwannadev.shop.core.domain.modules.option.model.ProductOptionCombination;
import alliwannadev.shop.core.domain.modules.option.service.ProductOptionCombinationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartItemService {

    private final ProductOptionCombinationService productOptionCombinationService;

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
        ProductOptionCombination productOptionCombination =
                productOptionCombinationService
                        .getByCond(
                                createCartItemParam.productId(),
                                createCartItemParam.selectedOptions()
                        )
                        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_COMBINATION_NOT_FOUND));

        CartItem newCartItem =
                createCartItemParam.toEntity(
                        cart,
                        productOptionCombination.getProductOptionCombinationId()
                );
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
