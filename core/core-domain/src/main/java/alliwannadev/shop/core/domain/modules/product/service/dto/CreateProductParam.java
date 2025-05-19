package alliwannadev.shop.core.domain.modules.product.service.dto;

import alliwannadev.shop.core.domain.modules.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.core.domain.modules.option.service.dto.CreateProductOptionParam;

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
