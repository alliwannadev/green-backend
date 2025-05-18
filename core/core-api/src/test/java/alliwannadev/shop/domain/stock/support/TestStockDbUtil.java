package alliwannadev.shop.domain.stock.support;

import alliwannadev.shop.domain.stock.model.Stock;
import alliwannadev.shop.domain.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TestStockDbUtil {

    private final StockRepository stockRepository;

    @Transactional
    public void deleteAll() {
        stockRepository.deleteAll();
    }

    public Optional<Stock> getOneByCombinationId(Long productOptionCombinationId) {
        return stockRepository.findByProductOptionCombinationId(productOptionCombinationId);
    }
}
