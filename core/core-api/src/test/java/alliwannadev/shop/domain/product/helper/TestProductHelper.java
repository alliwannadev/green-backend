package alliwannadev.shop.domain.product.helper;

import alliwannadev.shop.domain.option.helper.TestProductOptionCombinationHelper;
import alliwannadev.shop.domain.option.helper.TestProductOptionHelper;
import alliwannadev.shop.domain.product.model.Product;
import alliwannadev.shop.domain.product.repository.ProductRepository;
import alliwannadev.shop.domain.product.service.dto.CreateProductParam;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class TestProductHelper {

    private final ProductRepository productRepository;

    private final TestProductOptionHelper testProductOptionHelper;
    private final TestProductOptionCombinationHelper testProductOptionCombinationHelper;

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

        testProductOptionHelper.createAll(
                product,
                createProductParam.options()
        );

        testProductOptionCombinationHelper.createAll(
                product,
                createProductParam.optionCombinations()
        );

        return product;
    }

    @Transactional(readOnly = true)
    public Product getFirstProductId() {
        Page<Product> page = productRepository.findAll(PageRequest.of(0, 1));
        return page.getContent().getFirst();
    }
}
