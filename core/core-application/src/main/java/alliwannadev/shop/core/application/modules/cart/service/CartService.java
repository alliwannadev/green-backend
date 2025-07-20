package alliwannadev.shop.core.application.modules.cart.service;

import alliwannadev.shop.core.jpa.cart.model.CartEntity;
import alliwannadev.shop.core.jpa.cart.model.CartItemEntity;
import alliwannadev.shop.core.jpa.cart.repository.CartItemJpaRepository;
import alliwannadev.shop.core.jpa.cart.repository.CartJpaRepository;
import alliwannadev.shop.core.jpa.user.model.UserEntity;
import alliwannadev.shop.core.jpa.user.repository.UserJpaRepository;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {

    private final UserJpaRepository userJpaRepository;
    private final CartJpaRepository cartJpaRepository;
    private final CartItemJpaRepository cartItemJpaRepository;

    @Transactional
    public CartEntity createIfNotExists(
            Long userId
    ) {
        Optional<CartEntity> foundCart = cartJpaRepository
                .findOneByUserId(userId);
        if (foundCart.isPresent()
        ) {
            return foundCart.get();
        } else {
            UserEntity foundUserEntity =
                    userJpaRepository
                            .findById(userId)
                            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
            return cartJpaRepository.save(
                    CartEntity.of(
                            foundUserEntity,
                            0L,
                            0L,
                            0L,
                            0L
                    ));
        }
    }

    @Transactional
    public void update(Long cartId) {
        CartEntity foundCartEntity = cartJpaRepository
                .findById(cartId)
                .orElseThrow(
                        () -> new BusinessException(ErrorCode.CART_NOT_FOUND)
                );

        Long totalQuantity = 0L;
        Long totalAmount = 0L;
        Long totalDiscountedAmount = 0L;
        Long totalPaymentAmount = 0L;

        List<CartItemEntity> foundCartItemEntities = cartItemJpaRepository.findAllByCartId(foundCartEntity.getCartId());
        for (CartItemEntity cartItemEntity : foundCartItemEntities) {
            totalQuantity += cartItemEntity.getQuantity();
            totalAmount += cartItemEntity.getAmount();
            totalDiscountedAmount += cartItemEntity.getDiscountedAmount();
            totalPaymentAmount += cartItemEntity.getPaymentAmount();
        }

        foundCartEntity.update(
                totalQuantity,
                totalAmount,
                totalDiscountedAmount,
                totalPaymentAmount
        );
    }
}
