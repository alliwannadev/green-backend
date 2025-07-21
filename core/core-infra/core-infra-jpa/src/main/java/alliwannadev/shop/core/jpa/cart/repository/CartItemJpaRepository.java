package alliwannadev.shop.core.jpa.cart.repository;

import alliwannadev.shop.core.jpa.cart.model.CartItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CartItemJpaRepository extends JpaRepository<CartItemEntity, Long> {

    @Query(
            """
            select  cartItem
            from    CartItemEntity cartItem
            where   cartItem.cart.cartId = :cartId
            """
    )
    List<CartItemEntity> findAllByCartId(Long cartId);

    @Query(
            """
            select  cartItem
            from    CartItemEntity cartItem
            where   cartItem.cart.cartId = :cartId
            and     cartItem.productId = :productId
            and     cartItem.selectedOptions = :selectedOptions
            """
    )
    Optional<CartItemEntity> findOne(
            Long cartId,
            Long productId,
            String selectedOptions
    );
}

