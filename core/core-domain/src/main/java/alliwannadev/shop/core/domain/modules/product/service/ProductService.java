package alliwannadev.shop.core.domain.modules.product.service;

import alliwannadev.shop.core.domain.common.dto.InfiniteScrollCond;
import alliwannadev.shop.core.domain.common.error.BusinessException;
import alliwannadev.shop.core.domain.common.error.ErrorCode;
import alliwannadev.shop.core.domain.modules.option.service.ProductOptionCombinationService;
import alliwannadev.shop.core.domain.modules.option.service.ProductOptionService;
import alliwannadev.shop.core.domain.modules.product.model.Product;
import alliwannadev.shop.core.domain.modules.product.repository.ProductQueryRepository;
import alliwannadev.shop.core.domain.modules.product.repository.ProductRepository;
import alliwannadev.shop.core.domain.modules.product.repository.dto.GetProductDto;
import alliwannadev.shop.core.domain.modules.product.service.dto.CreateProductParam;
import alliwannadev.shop.core.domain.modules.product.service.dto.GetProductListParam;
import alliwannadev.shop.core.domain.modules.product.service.dto.GetProductResult;
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

    private final ProductRepository productRepository;
    private final ProductQueryRepository productQueryRepository;

    @Transactional
    public void createOne(CreateProductParam createProductParam) {
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
                productQueryRepository
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
