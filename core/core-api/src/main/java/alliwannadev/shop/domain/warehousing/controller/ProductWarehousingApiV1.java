package alliwannadev.shop.domain.warehousing.controller;

import alliwannadev.shop.common.dto.OkResponse;
import alliwannadev.shop.domain.warehousing.controller.dto.CreateProductWarehousingRequestV1;
import alliwannadev.shop.domain.warehousing.service.ProductWarehousingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class ProductWarehousingApiV1 {

    private final ProductWarehousingService productWarehousingService;

    @PostMapping(ProductWarehousingApiPaths.V1_WAREHOUSING)
    public OkResponse<Void> create(
            @Valid @RequestBody CreateProductWarehousingRequestV1 request
    ) {
        productWarehousingService.create(request.toDto());
        return OkResponse.of("상품 입고를 완료했습니다.");
    }

}
