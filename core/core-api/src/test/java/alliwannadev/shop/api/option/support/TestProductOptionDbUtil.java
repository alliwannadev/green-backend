package alliwannadev.shop.api.option.support;

import alliwannadev.shop.core.domain.modules.option.model.ProductOption;
import alliwannadev.shop.core.domain.modules.option.repository.ProductOptionRepository;
import alliwannadev.shop.core.domain.modules.option.service.dto.CreateProductOptionParam;
import alliwannadev.shop.core.domain.modules.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Component
public class TestProductOptionDbUtil {

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

    @Transactional
    public void deleteAll() {
        productOptionRepository.deleteAll();
    }
}
