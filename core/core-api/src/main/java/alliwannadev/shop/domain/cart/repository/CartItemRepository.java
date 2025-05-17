package alliwannadev.shop.domain.cart.repository;

import alliwannadev.shop.domain.cart.model.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    @Query(
            """
            select  cartItem
            from    CartItem cartItem
            where   cartItem.cart.cartId = :cartId
            """
    )
    List<CartItem> findAllByCartId(Long cartId);

    @Query(
            """
            select  cartItem
            from    CartItem cartItem
            where   cartItem.cart.cartId = :cartId
            and     cartItem.productId = :productId
            and     cartItem.selectedOptions = :selectedOptions
            """
    )
    Optional<CartItem> findOne(
            Long cartId,
            Long productId,
            String selectedOptions
    );
}

