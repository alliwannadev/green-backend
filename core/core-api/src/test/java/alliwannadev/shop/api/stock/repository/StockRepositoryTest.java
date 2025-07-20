package alliwannadev.shop.api.stock.repository;

import alliwannadev.shop.common.TestConfig;
import alliwannadev.shop.common.TestContainers;
import alliwannadev.shop.core.application.common.error.BusinessException;
import alliwannadev.shop.core.application.common.error.ErrorCode;
import alliwannadev.shop.core.application.modules.stock.model.Stock;
import alliwannadev.shop.core.application.modules.stock.repository.StockRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("데이터베이스 - 재고")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Import(TestConfig.class)
@ActiveProfiles("test")
@DataJpaTest
class StockRepositoryTest extends TestContainers {

    @Autowired StockRepository stockRepository;

    @DisplayName("[데이터베이스] productOptionCombinationId를 이용하여 재고 1건을 조회한다.")
    @Test
    void findByProductOptionCombinationId() {
        // Given
        Long productOptionCombinationId = 1L;
        Long quantity = 10L;
        stockRepository.save(Stock.of(productOptionCombinationId, quantity));

        // When
        Stock stock = stockRepository
                .findByProductOptionCombinationId(productOptionCombinationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));

        // Then
        assertThat(stock.getProductOptionCombinationId()).isEqualTo(productOptionCombinationId);
        assertThat(stock.getQuantity()).isEqualTo(quantity);
    }
}
