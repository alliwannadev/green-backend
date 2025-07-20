package alliwannadev.shop.core.application.modules.cart.service;

import alliwannadev.shop.core.jpa.cart.model.CartEntity;
import alliwannadev.shop.core.jpa.cart.model.CartItemEntity;
import alliwannadev.shop.core.jpa.cart.repository.CartItemJpaRepository;
import alliwannadev.shop.core.jpa.cart.repository.CartJpaRepository;
import alliwannadev.shop.core.application.modules.cart.service.dto.CreateCartItemParam;
import alliwannadev.shop.core.application.modules.cart.service.dto.GetCartItemListResult;
import alliwannadev.shop.core.application.modules.cart.service.dto.UpdateCartItemParam;
import alliwannadev.shop.core.jpa.option.model.ProductOptionCombinationEntity;
import alliwannadev.shop.core.application.modules.option.service.ProductOptionCombinationService;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CartItemService {

    private final ProductOptionCombinationService productOptionCombinationService;

    private final CartJpaRepository cartJpaRepository;
    private final CartItemJpaRepository cartItemJpaRepository;

    @Transactional(readOnly = true)
    public GetCartItemListResult getAllByCartId(Long cartId) {
        CartEntity foundCartEntity = cartJpaRepository
                .findById(cartId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_NOT_FOUND));
        List<CartItemEntity> foundCartItemEntities = cartItemJpaRepository.findAllByCartId(cartId);

        return GetCartItemListResult.fromEntities(
                foundCartEntity,
                foundCartItemEntities
        );
    }

    @Transactional
    public void addOne(
            Long cartId,
            CartEntity cartEntity,
            CreateCartItemParam createCartItemParam
    ) {
        ProductOptionCombinationEntity productOptionCombination =
                productOptionCombinationService
                        .getByCond(
                                createCartItemParam.productId(),
                                createCartItemParam.selectedOptions()
                        )
                        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_COMBINATION_NOT_FOUND));

        CartItemEntity newCartItemEntity =
                createCartItemParam.toEntity(
                        cartEntity,
                        productOptionCombination.getProductOptionCombinationId()
                );
        cartItemJpaRepository
                .findOne(
                        cartId,
                        newCartItemEntity.getProductId(),
                        newCartItemEntity.getSelectedOptions()
                )
                .ifPresentOrElse(
                        cartItem -> cartItem.updateQuantity(newCartItemEntity.getQuantity()),
                        () -> cartItemJpaRepository.save(newCartItemEntity)
                );
    }

    @Transactional
    public void update(
            Long cartItemId,
            UpdateCartItemParam param
    ) {
        CartItemEntity foundCartItemEntity = cartItemJpaRepository
                .findById(cartItemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));
        foundCartItemEntity.update(
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
        CartItemEntity foundCartItemEntity = cartItemJpaRepository
                .findById(cartItemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.CART_ITEM_NOT_FOUND));
        cartItemJpaRepository.delete(foundCartItemEntity);
    }
}
