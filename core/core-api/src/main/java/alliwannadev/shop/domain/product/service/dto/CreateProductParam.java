package alliwannadev.shop.domain.product.service.dto;

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

    public record CreateProductOptionParam(
            String optionCode,
            String optionName,
            String optionValue
    ) {
    }

    public record CreateProductOptionCombinationParam(
            String optionCode1,
            String optionName1,
            String optionValue1,
            String optionCode2,
            String optionName2,
            String optionValue2,
            String optionCode3,
            String optionName3,
            String optionValue3,
            String optionCode4,
            String optionName4,
            String optionValue4,
            String optionCode5,
            String optionName5,
            String optionValue5,
            Long stockQuantity,
            String optionManagementCode
    ) {
    }
}
