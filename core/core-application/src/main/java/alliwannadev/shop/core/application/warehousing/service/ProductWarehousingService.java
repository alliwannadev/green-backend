package alliwannadev.shop.core.application.warehousing.service;

import alliwannadev.shop.core.application.common.util.DateTimeUtil;
import alliwannadev.shop.core.application.option.service.ProductOptionCombinationService;
import alliwannadev.shop.core.jpa.stock.model.StockEntity;
import alliwannadev.shop.core.application.stock.service.StockService;
import alliwannadev.shop.core.application.warehousing.service.dto.CreateWarehousingParam;
import alliwannadev.shop.core.jpa.warehousing.model.ProductWarehousingEntity;
import alliwannadev.shop.core.jpa.warehousing.repository.ProductWarehousingJpaRepository;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductWarehousingService {

    private final ProductOptionCombinationService productOptionCombinationService;
    private final StockService stockService;

    private final ProductWarehousingJpaRepository productWarehousingJpaRepository;

    @Transactional
    public void create(CreateWarehousingParam param) {
        productOptionCombinationService
                .getByCombinationId(param.productOptionCombinationId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_COMBINATION_NOT_FOUND));
        StockEntity foundStock =
                stockService
                        .getOneByCombinationId(param.productOptionCombinationId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));

        productWarehousingJpaRepository.save(
                ProductWarehousingEntity.of(
                        foundStock,
                        DateTimeUtil.toLocalDate(param.warehousingDate()),
                        param.productOptionCombinationId(),
                        param.quantity()
                )
        );

        stockService.increaseQuantity(
                foundStock.getStockId(),
                param.quantity()
        );
    }
}
