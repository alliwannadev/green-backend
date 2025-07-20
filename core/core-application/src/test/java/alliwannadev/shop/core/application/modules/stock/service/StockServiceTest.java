package alliwannadev.shop.core.application.modules.stock.service;

import alliwannadev.shop.core.jpa.stock.model.Stock;
import alliwannadev.shop.core.jpa.stock.repository.StockRepository;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@DisplayName("비즈니스 로직 - 재고")
@ExtendWith(MockitoExtension.class)
class StockServiceTest {

    @InjectMocks StockService stockService;
    @Mock StockRepository stockRepository;

    @DisplayName("[Service] 재고 ID를 이용하여 재고 1건을 조회한다.")
    @Test
    void getExistentStockByStockId() {
        // Given
        Long stockId = 1L;
        Long productOptionCombinationId = 1L;
        Long quantity = 10L;
        given(stockRepository.findById(stockId))
                .willReturn(
                        Optional.of(
                                StockMockFactory.buildStock(
                                        stockId,
                                        productOptionCombinationId,
                                        quantity
                                )
                        )
                );

        // When
        Stock stock = stockService
                .getOneByStockId(stockId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));

        // Then
        assertThat(stock.getStockId()).isEqualTo(stockId);
        assertThat(stock.getProductOptionCombinationId()).isEqualTo(productOptionCombinationId);
        assertThat(stock.getQuantity()).isEqualTo(quantity);
        then(stockRepository).should().findById(stockId);
    }

    @DisplayName("[Service] 존재하지 않는 재고 ID를 이용하여 재고 1건을 조회한다.")
    @Test
    void getNotExistentStockByStockId() {
        // Given
        Long stockId = 0L;
        given(stockRepository.findById(stockId))
                .willReturn(Optional.empty());

        // When
        Optional<Stock> stock = stockService.getOneByStockId(stockId);

        // Then
        assertThat(stock.isEmpty()).isTrue();
        then(stockRepository).should().findById(stockId);
    }

    @DisplayName("[Service] productOptionCombinationId를 이용하여 재고 1건을 조회한다.")
    @Test
    void getExistentStockByProductOptionCombinationId() {
        // Given
        Long stockId = 1L;
        Long productOptionCombinationId = 1L;
        Long quantity = 10L;
        given(stockRepository.findByProductOptionCombinationId(productOptionCombinationId))
                .willReturn(
                        Optional.of(
                                StockMockFactory.buildStock(
                                        stockId,
                                        productOptionCombinationId,
                                        quantity
                                )
                        )
                );

        // When
        Stock stock = stockService
                .getOneByCombinationId(stockId)
                .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));

        // Then
        assertThat(stock.getStockId()).isEqualTo(stockId);
        assertThat(stock.getProductOptionCombinationId()).isEqualTo(productOptionCombinationId);
        assertThat(stock.getQuantity()).isEqualTo(quantity);
        then(stockRepository).should().findByProductOptionCombinationId(productOptionCombinationId);
    }

    @DisplayName("[Service] 재고 1건을 생성한다.")
    @Test
    void createStockAndReturnStock() {
        // Given
        Long stockId = 1L;
        Long productOptionCombinationId = 1L;
        Long quantity = 10L;
        given(stockRepository.save(any(Stock.class)))
                .willReturn(
                        StockMockFactory.buildStock(
                                stockId,
                                productOptionCombinationId,
                                quantity
                        ));

        // When
        Stock stock = stockService
                .create(productOptionCombinationId, quantity);

        // Then
        assertThat(stock.getStockId()).isEqualTo(stockId);
        assertThat(stock.getProductOptionCombinationId()).isEqualTo(productOptionCombinationId);
        assertThat(stock.getQuantity()).isEqualTo(quantity);
        then(stockRepository).should().save(any(Stock.class));
    }

    @DisplayName("[Service] 유효하지 않은 파라미터를 전달하면 재고 생성에 실패한다.")
    @Test
    void failedToCreateStockByInvalidParams() {
        // Given
        Long productOptionCombinationId = 0L;
        Long quantity = 0L;

        // When
        Throwable thrown = catchThrowable(() -> stockService.create(productOptionCombinationId, quantity));

        // Then
        assertThat(thrown)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.INVALID_INPUT_VALUE.getMessage());
        then(stockRepository).shouldHaveNoInteractions();
    }

    @DisplayName("[Service] 재고 수량을 증가한다.")
    @Test
    void increaseStockQuantity() {
        // Given
        Long stockId = 1L;
        Long productOptionCombinationId = 1L;
        Long quantity = 10L;
        Long addedQuantity = 5L;
        Stock stock = StockMockFactory.buildStock(
                stockId,
                productOptionCombinationId,
                quantity
        );
        given(stockRepository.findById(stockId))
                .willReturn(Optional.of(stock));

        // When
        stockService.increaseQuantity(stockId, addedQuantity);

        // Then
        assertThat(stock.getStockId()).isEqualTo(stockId);
        assertThat(stock.getProductOptionCombinationId()).isEqualTo(productOptionCombinationId);
        assertThat(stock.getQuantity()).isEqualTo(quantity + addedQuantity);
    }

    @DisplayName("[Service] 존재하지 않는 재고의 수량을 증가하려고 시도하면 예외가 발생한다.")
    @Test
    void increaseQuantityOfNotExistentStock() {
        // Given
        Long stockId = 0L;
        Long addedQuantity = 5L;
        given(stockRepository.findById(stockId))
                .willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> stockService.increaseQuantity(stockId, addedQuantity));

        // Then
        assertThat(thrown)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.STOCK_NOT_FOUND.getMessage());
    }

    @DisplayName("[Service] 재고 수량을 감소한다.")
    @Test
    void decreaseStockQuantity() {
        // Given
        Long stockId = 1L;
        Long productOptionCombinationId = 1L;
        Long quantity = 10L;
        Long decreasedQuantity = 5L;
        Stock stock = StockMockFactory.buildStock(
                stockId,
                productOptionCombinationId,
                quantity
        );
        given(stockRepository.findById(stockId))
                .willReturn(Optional.of(stock));

        // When
        stockService.decreaseQuantity(stockId, decreasedQuantity);

        // Then
        assertThat(stock.getStockId()).isEqualTo(stockId);
        assertThat(stock.getProductOptionCombinationId()).isEqualTo(productOptionCombinationId);
        assertThat(stock.getQuantity()).isEqualTo(quantity - decreasedQuantity);
    }

    @DisplayName("[Service] 존재하지 않는 재고의 수량을 감소하려고 시도하면 예외가 발생한다.")
    @Test
    void decreaseQuantityOfNotExistentStock() {
        // Given
        Long stockId = 0L;
        Long decreasedQuantity = 5L;
        given(stockRepository.findById(stockId))
                .willReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> stockService.decreaseQuantity(stockId, decreasedQuantity));

        // Then
        assertThat(thrown)
                .isInstanceOf(BusinessException.class)
                .hasMessageContaining(ErrorCode.STOCK_NOT_FOUND.getMessage());
    }
}
