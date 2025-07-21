package alliwannadev.shop.core.application.option.service;

import alliwannadev.shop.core.jpa.option.model.ProductOptionCombinationEntity;
import alliwannadev.shop.core.jpa.option.repository.ProductOptionCombinationJpaRepository;
import alliwannadev.shop.core.application.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.core.jpa.product.model.ProductEntity;
import alliwannadev.shop.core.application.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductOptionCombinationService {

    private final StockService stockService;

    private final ProductOptionCombinationJpaRepository productOptionCombinationJpaRepository;

    @Transactional
    public void createAll(
            ProductEntity product,
            List<CreateProductOptionCombinationParam> combinationParams
    ) {
        List<ProductOptionCombinationEntity> optionCombinations = combinationParams
                .stream()
                .map(combinationParam -> combinationParam.toEntity(product))
                .toList();

        productOptionCombinationJpaRepository.saveAll(optionCombinations);
        for (ProductOptionCombinationEntity optionCombination : optionCombinations) {
            stockService.create(optionCombination.getProductOptionCombinationId(), 0L);
        }
    }

    @Transactional(readOnly = true)
    public Optional<ProductOptionCombinationEntity> getByCombinationId(Long productCombinationId) {
        return productOptionCombinationJpaRepository.findById(productCombinationId);
    }

    @Transactional(readOnly = true)
    public Optional<ProductOptionCombinationEntity> getByCond(
            Long productId,
            String selectedOptions
    ) {
        return productOptionCombinationJpaRepository.findByCond(productId, selectedOptions);
    }
}
