package alliwannadev.shop.core.application.product.service;

import alliwannadev.shop.core.domain.common.dto.InfiniteScrollCond;
import alliwannadev.shop.core.application.option.service.ProductOptionCombinationService;
import alliwannadev.shop.core.application.option.service.ProductOptionService;
import alliwannadev.shop.core.jpa.product.model.ProductEntity;
import alliwannadev.shop.core.jpa.product.repository.ProductQueryJpaRepository;
import alliwannadev.shop.core.jpa.product.repository.ProductJpaRepository;
import alliwannadev.shop.core.jpa.product.repository.dto.GetProductDto;
import alliwannadev.shop.core.application.product.service.dto.CreateProductParam;
import alliwannadev.shop.core.application.product.service.dto.GetProductListParam;
import alliwannadev.shop.core.application.product.service.dto.GetProductResult;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductOptionService productOptionService;
    private final ProductOptionCombinationService productOptionCombinationService;

    private final ProductJpaRepository productJpaRepository;
    private final ProductQueryJpaRepository productQueryJpaRepository;

    @Transactional
    public void createOne(CreateProductParam createProductParam) {
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

        productOptionService.createAll(
                product,
                createProductParam.options()
        );
        productOptionCombinationService.createAll(
                product,
                createProductParam.optionCombinations()
        );
    }

    @Transactional(readOnly = true)
    public Page<GetProductResult> getAll(
            GetProductListParam param,
            Pageable pageable
    ) {
        Page<GetProductDto> result =
                productQueryJpaRepository
                        .findAll(
                                param.toCondition(param.categoryPath()),
                                pageable
                        );

        return result.map(GetProductResult::fromDto);
    }

    @Transactional(readOnly = true)
    public List<GetProductResult> getAllInfiniteScroll(
            String categoryPath,
            InfiniteScrollCond infiniteScrollCond
    ) {
        List<GetProductDto> result = productQueryJpaRepository.findAllInfiniteScroll(
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
        ProductEntity foundProduct =
                productJpaRepository
                        .findById(productId)
                        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_NOT_FOUND));

        return GetProductResult.fromEntity(foundProduct);
    }
}
