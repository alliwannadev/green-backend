package alliwannadev.shop.core.application.modules.stock.service;

import alliwannadev.shop.core.application.common.util.CustomValidator;
import alliwannadev.shop.core.jpa.stock.model.Stock;
import alliwannadev.shop.core.jpa.stock.repository.StockRepository;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
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
            Long productOptionCombinationId,
            Long quantity
    ) {
        CustomValidator.isPositiveNumber(productOptionCombinationId);
        Stock stock = Stock.of(
                productOptionCombinationId,
                quantity
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
