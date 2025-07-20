package alliwannadev.shop.core.application.modules.option.service.dto;

import alliwannadev.shop.core.jpa.option.model.ProductOption;
import alliwannadev.shop.core.jpa.product.model.Product;

public record CreateProductOptionParam(
        String optionCode,
        String optionName,
        String optionValue
) {

    public ProductOption toEntity(Product product) {
        return ProductOption.of(
                product,
                optionCode,
                optionName,
                optionValue
        );
    }
}
