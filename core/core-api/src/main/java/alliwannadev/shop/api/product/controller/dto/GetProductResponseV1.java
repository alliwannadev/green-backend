package alliwannadev.shop.api.product.controller.dto;

import alliwannadev.shop.core.application.modules.product.service.dto.GetProductResult;

public record GetProductResponseV1(
        Long productId,
        String productCode,
        String productName,
        String modelName,
        Long originalPrice,
        Long sellingPrice,
        String description,
        Boolean isDisplayed
) {

    public static GetProductResponseV1 fromDto(GetProductResult dto) {
        return new GetProductResponseV1(
                dto.productId(),
                dto.productCode(),
                dto.productName(),
                dto.modelName(),
                dto.originalPrice(),
                dto.sellingPrice(),
                dto.description(),
                dto.isDisplayed()
        );
    }
}
