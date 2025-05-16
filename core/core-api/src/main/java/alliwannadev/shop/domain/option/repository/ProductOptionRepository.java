package alliwannadev.shop.domain.option.repository;

import alliwannadev.shop.domain.option.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
