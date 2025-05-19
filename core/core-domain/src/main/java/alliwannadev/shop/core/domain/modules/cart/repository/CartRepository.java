package alliwannadev.shop.core.domain.modules.cart.repository;

import alliwannadev.shop.core.domain.modules.cart.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {

    @Query(
            """
            select  cart
            from    Cart cart
            where   cart.user.userId = :userId
            """
    )
    Optional<Cart> findOneByUserId(Long userId);
}
