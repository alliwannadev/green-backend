package alliwannadev.shop.core.domain.modules.product.repository;

import alliwannadev.shop.core.domain.modules.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
