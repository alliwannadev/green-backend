package alliwannadev.shop.domain.product.repository;

import alliwannadev.shop.domain.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
