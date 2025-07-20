package alliwannadev.shop.core.application.modules.product.service.dto;

import alliwannadev.shop.core.jpa.product.model.Product;
import alliwannadev.shop.core.jpa.product.repository.dto.GetProductDto;
import com.querydsl.core.annotations.QueryProjection;

public record GetProductResult(
        Long productId,
        String productCode,
        String productName,
        String modelName,
        Long originalPrice,
        Long sellingPrice,
        String description,
        Boolean isDisplayed
) {

    @QueryProjection
    public GetProductResult {
    }

    public static GetProductResult fromDto(GetProductDto dto) {
        return new GetProductResult(
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

    public static GetProductResult fromEntity(Product product) {
        return new GetProductResult(
                product.getProductId(),
                product.getProductCode(),
                product.getProductName(),
                product.getModelName(),
                product.getOriginalPrice(),
                product.getSellingPrice(),
                product.getDescription(),
                product.getIsDisplayed()
        );
    }
}
