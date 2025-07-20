package alliwannadev.shop.api.product.support;

import alliwannadev.shop.api.option.support.TestProductOptionCombinationDbUtil;
import alliwannadev.shop.api.option.support.TestProductOptionDbUtil;
import alliwannadev.shop.core.application.modules.product.service.dto.CreateProductParam;
import alliwannadev.shop.core.jpa.product.model.Product;
import alliwannadev.shop.core.jpa.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class TestProductDbUtil {

    private final ProductRepository productRepository;

    private final TestProductOptionDbUtil testProductOptionDbUtil;
    private final TestProductOptionCombinationDbUtil testProductOptionCombinationDbUtil;

    @Transactional
    public Product createProduct(CreateProductParam createProductParam) {
        Product product = productRepository.save(
                Product.of(
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
        productRepository.deleteAll();
    }

    @Transactional(readOnly = true)
    public Product getFirstProductId() {
        Page<Product> page = productRepository.findAll(PageRequest.of(0, 1));
        return page.getContent().getFirst();
    }
}
