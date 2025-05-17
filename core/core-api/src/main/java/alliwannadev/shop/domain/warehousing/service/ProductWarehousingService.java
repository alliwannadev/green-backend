package alliwannadev.shop.domain.warehousing.service;

import alliwannadev.shop.common.error.BusinessException;
import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.common.util.DateTimeUtil;
import alliwannadev.shop.domain.option.service.ProductOptionCombinationService;
import alliwannadev.shop.domain.stock.model.Stock;
import alliwannadev.shop.domain.stock.service.StockService;
import alliwannadev.shop.domain.warehousing.model.ProductWarehousing;
import alliwannadev.shop.domain.warehousing.repository.ProductWarehousingRepository;
import alliwannadev.shop.domain.warehousing.service.dto.CreateWarehousingParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

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
        Optional<Stock> findStock = stockService.getOneByCombinationId(param.productOptionCombinationId());
        Stock foundStock = findStock.orElseGet(() -> stockService.create(param.productOptionCombinationId()));

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
