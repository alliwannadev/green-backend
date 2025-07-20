package alliwannadev.shop.api.option.support;

import alliwannadev.shop.core.application.modules.option.service.ProductOptionCombinationService;
import alliwannadev.shop.core.application.modules.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.core.jpa.option.model.ProductOptionCombinationEntity;
import alliwannadev.shop.core.jpa.option.repository.ProductOptionCombinationJpaRepository;
import alliwannadev.shop.core.jpa.product.model.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class TestProductOptionCombinationDbUtil {

    private final ProductOptionCombinationService productOptionCombinationService;
    private final ProductOptionCombinationJpaRepository productOptionCombinationJpaRepository;

    @Transactional
    public void createAll(
            ProductEntity product,
            List<CreateProductOptionCombinationParam> combinationParams
    ) {
        productOptionCombinationService.createAll(product, combinationParams);
    }

    @Transactional
    public Optional<ProductOptionCombinationEntity> getByCond(
            Long productId,
            String selectedOptions
    ) {
        return productOptionCombinationJpaRepository.findByCond(productId, selectedOptions);
    }

    @Transactional
    public void deleteAll() {
        productOptionCombinationJpaRepository.deleteAll();
    }
}
