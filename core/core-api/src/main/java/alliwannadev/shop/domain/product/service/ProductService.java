package alliwannadev.shop.domain.product.service;

import alliwannadev.shop.domain.product.model.Product;
import alliwannadev.shop.domain.product.repository.ProductRepository;
import alliwannadev.shop.domain.product.service.dto.CreateProductParam;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void createOne(CreateProductParam createProductParam) {
        productRepository.save(
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
    }
}
