package alliwannadev.shop.api.stock.repository;

import alliwannadev.shop.common.TestConfig;
import alliwannadev.shop.common.TestContainers;
import alliwannadev.shop.core.jpa.stock.model.StockEntity;
import alliwannadev.shop.core.jpa.stock.repository.StockJpaRepository;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
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
class StockJpaRepositoryTest extends TestContainers {

    @Autowired
    StockJpaRepository stockJpaRepository;

    @DisplayName("[데이터베이스] productOptionCombinationId를 이용하여 재고 1건을 조회한다.")
    @Test
    void findByProductOptionCombinationId() {
        // Given
        Long productOptionCombinationId = 1L;
        Long quantity = 10L;
        stockJpaRepository.save(StockEntity.of(productOptionCombinationId, quantity));

        // When
        StockEntity stock = stockJpaRepository
                .findByProductOptionCombinationId(productOptionCombinationId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));

        // Then
        assertThat(stock.getProductOptionCombinationId()).isEqualTo(productOptionCombinationId);
        assertThat(stock.getQuantity()).isEqualTo(quantity);
    }
}
