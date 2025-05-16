package alliwannadev.shop.domain.option.service.dto;

import alliwannadev.shop.domain.option.model.ProductOption;
import alliwannadev.shop.domain.product.model.Product;

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
