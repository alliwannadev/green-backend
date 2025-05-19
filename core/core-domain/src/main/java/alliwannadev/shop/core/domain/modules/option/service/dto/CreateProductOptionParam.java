package alliwannadev.shop.core.domain.modules.option.service.dto;

import alliwannadev.shop.core.domain.modules.option.model.ProductOption;
import alliwannadev.shop.core.domain.modules.product.model.Product;

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
