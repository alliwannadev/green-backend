package alliwannadev.shop.core.jpa.stock.repository;

import alliwannadev.shop.core.jpa.stock.model.StockEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockJpaRepository extends JpaRepository<StockEntity, Long> {

    Optional<StockEntity> findByProductOptionCombinationId(Long productOptionCombinationId);
}
