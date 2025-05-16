package alliwannadev.shop.domain.product.repository.dto;

import com.querydsl.core.annotations.QueryProjection;

public record GetProductDto(
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
    public GetProductDto {
    }
}
