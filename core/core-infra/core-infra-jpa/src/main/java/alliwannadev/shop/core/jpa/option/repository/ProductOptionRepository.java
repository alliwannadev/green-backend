package alliwannadev.shop.core.jpa.option.repository;

import alliwannadev.shop.core.jpa.option.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
