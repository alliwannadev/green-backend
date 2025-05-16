package alliwannadev.shop.domain.option.helper;

import alliwannadev.shop.domain.option.model.ProductOptionCombination;
import alliwannadev.shop.domain.option.repository.ProductOptionCombinationRepository;
import alliwannadev.shop.domain.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TestProductOptionCombinationHelper {

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
    }
}
