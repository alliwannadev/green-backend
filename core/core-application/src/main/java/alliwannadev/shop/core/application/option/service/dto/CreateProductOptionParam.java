package alliwannadev.shop.core.application.option.service.dto;

import alliwannadev.shop.core.jpa.option.model.ProductOptionEntity;
import alliwannadev.shop.core.jpa.product.model.ProductEntity;

public record CreateProductOptionParam(
        String optionCode,
        String optionName,
        String optionValue
) {

    public ProductOptionEntity toEntity(ProductEntity product) {
        return ProductOptionEntity.of(
                product,
                optionCode,
                optionName,
                optionValue
        );
    }
}
