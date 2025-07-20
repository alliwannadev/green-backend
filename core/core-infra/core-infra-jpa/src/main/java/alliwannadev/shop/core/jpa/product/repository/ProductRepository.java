package alliwannadev.shop.core.jpa.product.repository;

import alliwannadev.shop.core.jpa.product.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
