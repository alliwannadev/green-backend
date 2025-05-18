package alliwannadev.shop.domain.option.support;

import alliwannadev.shop.domain.option.model.ProductOptionCombination;
import alliwannadev.shop.domain.option.repository.ProductOptionCombinationRepository;
import alliwannadev.shop.domain.option.service.ProductOptionCombinationService;
import alliwannadev.shop.domain.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.domain.product.model.Product;
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
