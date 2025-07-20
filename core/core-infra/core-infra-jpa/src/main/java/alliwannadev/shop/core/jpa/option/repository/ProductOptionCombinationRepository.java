package alliwannadev.shop.core.jpa.option.repository;

import alliwannadev.shop.core.jpa.option.model.ProductOptionCombination;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ProductOptionCombinationRepository extends JpaRepository<ProductOptionCombination, Long> {

    @Query(
            """
            select  poc
            from    ProductOptionCombination poc
            where   poc.product.productId = :productId
            and     poc.selectedOptions = :selectedOptions
            """
    )
    Optional<ProductOptionCombination> findByCond(
            Long productId,
            String selectedOptions
    );
}
