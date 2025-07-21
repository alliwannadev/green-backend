package alliwannadev.shop.core.jpa.warehousing.repository;

import alliwannadev.shop.core.jpa.warehousing.model.ProductWarehousingEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductWarehousingJpaRepository extends JpaRepository<ProductWarehousingEntity, Long> {
}
