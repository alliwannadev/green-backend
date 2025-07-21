package alliwannadev.shop.core.application.product.service.dto;

import alliwannadev.shop.core.application.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.core.application.option.service.dto.CreateProductOptionParam;

import java.util.List;

public record CreateProductParam(
        Long categoryId,
        String productCode,
        String productName,
        String modelName,
        Long originalPrice,
        Long sellingPrice,
        String description,
        List<CreateProductOptionParam> options,
        List<CreateProductOptionCombinationParam> optionCombinations
) {
}
