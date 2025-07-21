package alliwannadev.shop.core.application.modules.stock.service;

import alliwannadev.shop.core.jpa.stock.model.StockEntity;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.test.util.ReflectionTestUtils;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StockMockFactory {

    public static StockEntity buildStock(
            Long stockId,
            Long productOptionCombinationId,
            Long quantity
    ) {
        StockEntity stock = StockEntity.of(productOptionCombinationId, quantity);
        ReflectionTestUtils.setField(stock, "stockId", stockId);
        return stock;
    }
}
