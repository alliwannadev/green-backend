package alliwannadev.shop.core.application.modules.cart.service;

import alliwannadev.shop.core.jpa.cart.model.Cart;
import alliwannadev.shop.core.jpa.cart.model.CartItem;
import alliwannadev.shop.core.jpa.cart.repository.CartItemRepository;
import alliwannadev.shop.core.jpa.cart.repository.CartRepository;
import alliwannadev.shop.core.jpa.user.domain.User;
import alliwannadev.shop.core.jpa.user.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final CartRepository cartRepository;
    private final CartItemRepository cartItemRepository;

    @Transactional
    public Cart createIfNotExists(
            Long userId
    ) {
        Optional<Cart> foundCart = cartRepository
                .findOneByUserId(userId);
        if (foundCart.isPresent()
        ) {
            return foundCart.get();
        } else {
            User foundUser =
                    userRepository
                            .findById(userId)
                            .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
            return cartRepository.save(
                    Cart.of(
                            foundUser,
                            0L,
                            0L,
                            0L,
                            0L
                    ));
        }
    }

    @Transactional
    public void update(Long cartId) {
        Cart foundCart = cartRepository
                .findById(cartId)
                .orElseThrow(
                        () -> new BusinessException(ErrorCode.CART_NOT_FOUND)
                );

        Long totalQuantity = 0L;
        Long totalAmount = 0L;
        Long totalDiscountedAmount = 0L;
        Long totalPaymentAmount = 0L;

        List<CartItem> foundCartItems = cartItemRepository.findAllByCartId(foundCart.getCartId());
        for (CartItem cartItem : foundCartItems) {
            totalQuantity += cartItem.getQuantity();
            totalAmount += cartItem.getAmount();
            totalDiscountedAmount += cartItem.getDiscountedAmount();
            totalPaymentAmount += cartItem.getPaymentAmount();
        }

        foundCart.update(
                totalQuantity,
                totalAmount,
                totalDiscountedAmount,
                totalPaymentAmount
        );
    }
}
