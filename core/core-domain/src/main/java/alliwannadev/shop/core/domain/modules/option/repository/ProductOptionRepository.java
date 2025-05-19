package alliwannadev.shop.core.domain.modules.option.repository;

import alliwannadev.shop.core.domain.modules.option.model.ProductOption;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOptionRepository extends JpaRepository<ProductOption, Long> {
}
