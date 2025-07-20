package alliwannadev.shop.core.domain.modules.option.service;

import alliwannadev.shop.core.domain.modules.option.model.ProductOptionCombination;
import alliwannadev.shop.core.domain.modules.option.repository.ProductOptionCombinationRepository;
import alliwannadev.shop.core.domain.modules.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.core.domain.modules.product.model.Product;
import alliwannadev.shop.core.domain.modules.stock.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductOptionCombinationService {

    private final StockService stockService;

    private final ProductOptionCombinationRepository productOptionCombinationRepository;

    @Transactional
    public void createAll(
            Product product,
            List<CreateProductOptionCombinationParam> combinationParams
    ) {
        List<ProductOptionCombination> optionCombinations = combinationParams
                .stream()
                .map(combinationParam -> combinationParam.toEntity(product))
                .toList();

        productOptionCombinationRepository.saveAll(optionCombinations);
        for (ProductOptionCombination optionCombination : optionCombinations) {
            stockService.create(optionCombination.getProductOptionCombinationId(), 0L);
        }
    }

    @Transactional(readOnly = true)
    public Optional<ProductOptionCombination> getByCombinationId(Long productCombinationId) {
        return productOptionCombinationRepository.findById(productCombinationId);
    }

    @Transactional(readOnly = true)
    public Optional<ProductOptionCombination> getByCond(
            Long productId,
            String selectedOptions
    ) {
        return productOptionCombinationRepository.findByCond(productId, selectedOptions);
    }
}
