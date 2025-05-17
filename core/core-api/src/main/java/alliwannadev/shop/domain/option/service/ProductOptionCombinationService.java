package alliwannadev.shop.domain.option.service;

import alliwannadev.shop.domain.option.model.ProductOptionCombination;
import alliwannadev.shop.domain.option.repository.ProductOptionCombinationRepository;
import alliwannadev.shop.domain.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ProductOptionCombinationService {

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
