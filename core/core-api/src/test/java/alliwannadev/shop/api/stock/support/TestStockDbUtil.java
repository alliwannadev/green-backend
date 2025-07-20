package alliwannadev.shop.api.stock.support;

import alliwannadev.shop.core.jpa.stock.model.StockEntity;
import alliwannadev.shop.core.jpa.stock.repository.StockJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TestStockDbUtil {

    private final StockJpaRepository stockJpaRepository;

    @Transactional
    public void deleteAll() {
        stockJpaRepository.deleteAll();
    }

    public Optional<StockEntity> getOneByCombinationId(Long productOptionCombinationId) {
        return stockJpaRepository.findByProductOptionCombinationId(productOptionCombinationId);
    }
}
