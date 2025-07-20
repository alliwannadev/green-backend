package alliwannadev.shop.core.jpa.cart.repository;

import alliwannadev.shop.core.jpa.cart.model.CartEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface CartJpaRepository extends JpaRepository<CartEntity, Long> {

    @Query(
            """
            select  cart
            from    CartEntity cart
            where   cart.user.userId = :userId
            """
    )
    Optional<CartEntity> findOneByUserId(Long userId);
}
