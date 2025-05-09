package alliwannadev.shop.domain.product.controller;

import alliwannadev.shop.common.dto.OkResponse;
import alliwannadev.shop.domain.product.controller.dto.CreateProductRequestV1;
import alliwannadev.shop.domain.product.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
}
