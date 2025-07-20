package alliwannadev.shop.core.application.modules.stock.service;

import alliwannadev.shop.core.application.common.util.CustomValidator;
import alliwannadev.shop.core.jpa.stock.model.StockEntity;
import alliwannadev.shop.core.jpa.stock.repository.StockJpaRepository;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class StockService {

    private final StockJpaRepository stockJpaRepository;

    @Transactional(readOnly = true)
    public Optional<StockEntity> getOneByStockId(Long stockId) {
        return stockJpaRepository.findById(stockId);
    }

    @Transactional(readOnly = true)
    public Optional<StockEntity> getOneByCombinationId(Long productOptionCombinationId) {
        return stockJpaRepository.findByProductOptionCombinationId(productOptionCombinationId);
    }

    @Transactional
    public StockEntity create(
            Long productOptionCombinationId,
            Long quantity
    ) {
        CustomValidator.isPositiveNumber(productOptionCombinationId);
        StockEntity stock = StockEntity.of(
                productOptionCombinationId,
                quantity
        );
        return stockJpaRepository.save(stock);
    }

    @Transactional
    public void increaseQuantity(
            Long stockId,
            Long quantity
    ) {
        StockEntity stock =
                getOneByStockId(stockId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));
        stock.increaseQuantity(quantity);
    }

    @Transactional
    public void decreaseQuantity(
            Long stockId,
            Long quantity
    ) {
        StockEntity stock =
                getOneByStockId(stockId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));
        stock.decreaseQuantity(quantity);
    }
}
