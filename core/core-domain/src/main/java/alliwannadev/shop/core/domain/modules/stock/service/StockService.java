package alliwannadev.shop.core.domain.modules.stock.service;

import alliwannadev.shop.core.domain.common.error.BusinessException;
import alliwannadev.shop.core.domain.common.error.ErrorCode;
import alliwannadev.shop.core.domain.modules.stock.model.Stock;
import alliwannadev.shop.core.domain.modules.stock.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockRepository stockRepository;

    @Transactional(readOnly = true)
    public Optional<Stock> getOneByStockId(Long stockId) {
        return stockRepository.findById(stockId);
    }

    @Transactional(readOnly = true)
    public Optional<Stock> getOneByCombinationId(Long productOptionCombinationId) {
        return stockRepository.findByProductOptionCombinationId(productOptionCombinationId);
    }

    @Transactional
    public Stock create(
            Long productOptionCombinationId
    ) {
        Stock stock = Stock.of(
                productOptionCombinationId,
                0L
        );
        return stockRepository.save(stock);
    }

    @Transactional
    public void increaseQuantity(
            Long stockId,
            Long quantity
    ) {
        Stock stock =
                getOneByStockId(stockId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));
        stock.increaseQuantity(quantity);
    }

    @Transactional
    public void decreaseQuantity(
            Long stockId,
            Long quantity
    ) {
        Stock stock =
                getOneByStockId(stockId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));
        stock.decreaseQuantity(quantity);
    }
}
