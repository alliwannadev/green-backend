package alliwannadev.shop.api.product.support;

import alliwannadev.shop.api.option.support.TestProductOptionCombinationDbUtil;
import alliwannadev.shop.api.option.support.TestProductOptionDbUtil;
import alliwannadev.shop.core.application.product.service.dto.CreateProductParam;
import alliwannadev.shop.core.jpa.product.model.ProductEntity;
import alliwannadev.shop.core.jpa.product.repository.ProductJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class TestProductDbUtil {

    private final ProductJpaRepository productJpaRepository;

    private final TestProductOptionDbUtil testProductOptionDbUtil;
    private final TestProductOptionCombinationDbUtil testProductOptionCombinationDbUtil;

    @Transactional
    public ProductEntity createProduct(CreateProductParam createProductParam) {
        ProductEntity product = productJpaRepository.save(
                ProductEntity.of(
                        createProductParam.productCode(),
                        createProductParam.productName(),
                        createProductParam.modelName(),
                        createProductParam.originalPrice(),
                        createProductParam.sellingPrice(),
                        createProductParam.description(),
                        true
                )
        );

        testProductOptionDbUtil.createAll(
                product,
                createProductParam.options()
        );

        testProductOptionCombinationDbUtil.createAll(
                product,
                createProductParam.optionCombinations()
        );

        return product;
    }

    @Transactional
    public void deleteAll() {
        productJpaRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public ProductEntity getFirstProductId() {
        Page<ProductEntity> page = productJpaRepository.findAll(PageRequest.of(0, 1));
        return page.getContent().getFirst();
    }
}
