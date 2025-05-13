package alliwannadev.shop.domain.option.helper;

import alliwannadev.shop.domain.option.model.ProductOption;
import alliwannadev.shop.domain.option.repository.ProductOptionRepository;
import alliwannadev.shop.domain.option.service.dto.CreateProductOptionParam;
import alliwannadev.shop.domain.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TestProductOptionHelper {

    private final ProductOptionRepository productOptionRepository;

    @Transactional
    public void createAll(
            Product product,
            List<CreateProductOptionParam> createProductOptionParams
    ) {
        List<ProductOption> productOptions = createProductOptionParams
                .stream()
                .map(optionParam -> optionParam.toEntity(product))
                .toList();

        productOptionRepository.saveAll(productOptions);
    }
}
