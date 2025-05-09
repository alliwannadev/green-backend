package alliwannadev.shop.domain.product.service;

import alliwannadev.shop.common.dto.InfiniteScrollCond;
import alliwannadev.shop.common.error.BusinessException;
import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.domain.product.model.Product;
import alliwannadev.shop.domain.product.repository.ProductQueryRepository;
import alliwannadev.shop.domain.product.repository.ProductRepository;
import alliwannadev.shop.domain.product.repository.dto.GetProductDto;
import alliwannadev.shop.domain.product.service.dto.CreateProductParam;
import alliwannadev.shop.domain.product.service.dto.GetProductListParam;
import alliwannadev.shop.domain.product.service.dto.GetProductResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;

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

    @Transactional(readOnly = true)
    public Page<GetProductResult> getAll(
            GetProductListParam getProductListParam,
            Pageable pageable
    ) {
        Page<GetProductDto> result = productQueryRepository.findAll(
                getProductListParam.toCondition(getProductListParam.categoryPath()),
                pageable
        );

        return result.map(GetProductResult::fromDto);
    }

    @Transactional(readOnly = true)
    public List<GetProductResult> getAllInfiniteScroll(
            String categoryPath,
            InfiniteScrollCond infiniteScrollCond
    ) {
        List<GetProductDto> result = productQueryRepository.findAllInfiniteScroll(
                categoryPath,
                infiniteScrollCond
        );

        return result
                .stream()
                .map(GetProductResult::fromDto)
                .toList();
    }

    @Transactional(readOnly = true)
    public GetProductResult getOneByProductId(
            Long productId
    ) {
        Product foundProduct =
                productRepository
                        .findById(productId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        return GetProductResult.fromEntity(foundProduct);
    }
}
