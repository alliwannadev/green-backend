package alliwannadev.shop.api.option.support;

import alliwannadev.shop.core.application.modules.option.model.ProductOptionCombination;
import alliwannadev.shop.core.application.modules.option.repository.ProductOptionCombinationRepository;
import alliwannadev.shop.core.application.modules.option.service.ProductOptionCombinationService;
import alliwannadev.shop.core.application.modules.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.core.application.modules.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TestProductOptionCombinationDbUtil {

    private final ProductOptionCombinationService productOptionCombinationService;
    private final ProductOptionCombinationRepository productOptionCombinationRepository;

    @Transactional
    public void createAll(
            Product product,
            List<CreateProductOptionCombinationParam> combinationParams
    ) {
        productOptionCombinationService.createAll(product, combinationParams);
    }

    @Transactional
    public Optional<ProductOptionCombination> getByCond(
            Long productId,
            String selectedOptions
    ) {
        return productOptionCombinationRepository.findByCond(productId, selectedOptions);
    }

    @Transactional
    public void deleteAll() {
        productOptionCombinationRepository.deleteAll();
    }
}
