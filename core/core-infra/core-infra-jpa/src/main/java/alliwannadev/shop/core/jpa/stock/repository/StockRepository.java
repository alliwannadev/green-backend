package alliwannadev.shop.core.jpa.stock.repository;

import alliwannadev.shop.core.jpa.stock.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {

    Optional<Stock> findByProductOptionCombinationId(Long productOptionCombinationId);
}
