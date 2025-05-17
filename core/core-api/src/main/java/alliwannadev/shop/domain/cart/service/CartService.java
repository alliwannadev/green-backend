package alliwannadev.shop.domain.cart.service;

import alliwannadev.shop.common.error.BusinessException;
import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.domain.cart.model.Cart;
import alliwannadev.shop.domain.cart.model.CartItem;
import alliwannadev.shop.domain.cart.repository.CartItemRepository;
import alliwannadev.shop.domain.cart.repository.CartRepository;
import alliwannadev.shop.domain.user.domain.User;
import alliwannadev.shop.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CartService {

    private final UserService userService;

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
            User foundUser = userService.getOneByUserId(userId);
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
