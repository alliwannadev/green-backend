package alliwannadev.shop.core.jpa.option.repository;

import alliwannadev.shop.core.jpa.option.model.ProductOptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionJpaRepository extends JpaRepository<ProductOptionEntity, Long> {
}
