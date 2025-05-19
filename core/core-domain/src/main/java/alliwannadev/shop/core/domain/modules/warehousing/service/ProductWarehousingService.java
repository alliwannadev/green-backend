package alliwannadev.shop.core.domain.modules.warehousing.service;

import alliwannadev.shop.core.domain.common.error.BusinessException;
import alliwannadev.shop.core.domain.common.error.ErrorCode;
import alliwannadev.shop.core.domain.common.util.DateTimeUtil;
import alliwannadev.shop.core.domain.modules.option.service.ProductOptionCombinationService;
import alliwannadev.shop.core.domain.modules.stock.model.Stock;
import alliwannadev.shop.core.domain.modules.stock.service.StockService;
import alliwannadev.shop.core.domain.modules.warehousing.model.ProductWarehousing;
import alliwannadev.shop.core.domain.modules.warehousing.repository.ProductWarehousingRepository;
import alliwannadev.shop.core.domain.modules.warehousing.service.dto.CreateWarehousingParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductWarehousingService {

    private final ProductOptionCombinationService productOptionCombinationService;
    private final StockService stockService;

    private final ProductWarehousingRepository productWarehousingRepository;

    @Transactional
    public void create(CreateWarehousingParam param) {
        productOptionCombinationService
                .getByCombinationId(param.productOptionCombinationId())
                .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_COMBINATION_NOT_FOUND));
        Stock foundStock =
                stockService
                        .getOneByCombinationId(param.productOptionCombinationId())
                        .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));

        productWarehousingRepository.save(
                ProductWarehousing.of(
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
