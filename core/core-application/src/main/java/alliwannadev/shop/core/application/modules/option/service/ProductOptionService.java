package alliwannadev.shop.core.application.modules.option.service;

import alliwannadev.shop.core.jpa.option.model.ProductOptionEntity;
import alliwannadev.shop.core.jpa.option.repository.ProductOptionJpaRepository;
import alliwannadev.shop.core.application.modules.option.service.dto.CreateProductOptionParam;
import alliwannadev.shop.core.jpa.product.model.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductOptionService {

    private final ProductOptionJpaRepository productOptionJpaRepository;

    @Transactional
    public void createAll(
            ProductEntity product,
            List<CreateProductOptionParam> createProductOptionParams
    ) {
        List<ProductOptionEntity> productOptions = createProductOptionParams
                .stream()
                .map(optionParam -> optionParam.toEntity(product))
                .toList();

        productOptionJpaRepository.saveAll(productOptions);
    }
}
