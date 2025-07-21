package alliwannadev.shop.core.jpa.product.repository;

import alliwannadev.shop.core.jpa.product.model.ProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductJpaRepository extends JpaRepository<ProductEntity, Long> {
}
