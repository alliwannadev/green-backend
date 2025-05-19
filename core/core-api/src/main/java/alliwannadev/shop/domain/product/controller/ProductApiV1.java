package alliwannadev.shop.domain.product.controller;

import alliwannadev.shop.common.dto.*;
import alliwannadev.shop.domain.product.controller.dto.CreateProductRequestV1;
import alliwannadev.shop.domain.product.controller.dto.GetProductListRequestV1;
import alliwannadev.shop.domain.product.controller.dto.GetProductResponseV1;
import alliwannadev.shop.core.domain.modules.product.service.ProductService;
import alliwannadev.shop.core.domain.modules.product.service.dto.GetProductResult;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
public class ProductApiV1 {

    private final ProductService productService;

    @PostMapping(ProductApiPaths.V1_PRODUCTS)
    public OkResponse<Void> createProduct(
            @Valid @RequestBody CreateProductRequestV1 createProductRequestV1
    ) {
        productService.createOne(createProductRequestV1.toDto());
        return OkResponse.of("상품 등록을 완료했습니다.");
    }

    @GetMapping(ProductApiPaths.V1_PRODUCTS)
    public OkResponse<PaginationResponse<GetProductResponseV1>> getAll(
            @Valid @ModelAttribute PaginationRequest customPageRequestV1,
            @Valid @ModelAttribute GetProductListRequestV1 getProductListRequestV1
    ) {
        Page<GetProductResult> searchProductResultPage = productService.getAll(
                getProductListRequestV1.toDto(),
                customPageRequestV1.getPageable()
        );

        return OkResponse.of(
                PaginationResponse.of(
                        searchProductResultPage.map(GetProductResponseV1::fromDto),
                        customPageRequestV1
                )
        );
    }

    @GetMapping(ProductApiPaths.V1_PRODUCTS_INFINITE_SCROLL)
    public OkResponse<InfiniteScrollResponse<GetProductResponseV1>> getAllInfiniteScroll(
            @NotEmpty @RequestParam String categoryPath,
            @Valid @ModelAttribute InfiniteScrollRequest infiniteScrollRequest
    ) {
        List<GetProductResponseV1> result = productService
                .getAllInfiniteScroll(
                        categoryPath,
                        infiniteScrollRequest.toCond()
                )
                .stream()
                .map(GetProductResponseV1::fromDto)
                .toList();

        return OkResponse.of(
                InfiniteScrollResponse.of(
                        result,
                        infiniteScrollRequest.pageSize()
                )
        );
    }

    @GetMapping(ProductApiPaths.V1_PRODUCTS_BY_PRODUCT_ID)
    public OkResponse<GetProductResponseV1> getOneByProductId(
            @PathVariable @Positive Long productId
    ) {
        GetProductResult result = productService.getOneByProductId(productId);
        return OkResponse.of(GetProductResponseV1.fromDto(result));
    }
}
