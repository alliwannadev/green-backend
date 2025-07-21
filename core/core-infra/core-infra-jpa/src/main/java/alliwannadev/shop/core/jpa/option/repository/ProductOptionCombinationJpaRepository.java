package alliwannadev.shop.core.jpa.option.repository;

import alliwannadev.shop.core.jpa.option.model.ProductOptionCombinationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductOptionCombinationJpaRepository extends JpaRepository<ProductOptionCombinationEntity, Long> {

    @Query(
            """
            select  poc
            from    ProductOptionCombinationEntity poc
            where   poc.product.productId = :productId
            and     poc.selectedOptions = :selectedOptions
            """
    )
    Optional<ProductOptionCombinationEntity> findByCond(
            Long productId,
            String selectedOptions
    );
}
