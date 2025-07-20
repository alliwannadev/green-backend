package alliwannadev.shop.core.application.modules.option.service;

import alliwannadev.shop.core.jpa.option.model.ProductOption;
import alliwannadev.shop.core.jpa.option.repository.ProductOptionRepository;
import alliwannadev.shop.core.application.modules.option.service.dto.CreateProductOptionParam;
import alliwannadev.shop.core.jpa.product.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductOptionService {

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
