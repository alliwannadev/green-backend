package alliwannadev.shop.api.product.controller.dto;

import alliwannadev.shop.core.application.modules.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.core.application.modules.option.service.dto.CreateProductOptionParam;
import alliwannadev.shop.core.application.modules.product.service.dto.CreateProductParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateProductRequestV1(
        @NotNull Long categoryId,
        @NotBlank String productCode,
        @NotBlank String productName,
        @NotBlank String modelName,
        @NotNull Long originalPrice,
        @NotNull Long sellingPrice,
        @NotBlank String description,
        @NotNull List<CreateProductOption> options,
        @NotNull List<CreateProductOptionCombination> optionCombinations
) {

    public record CreateProductOption(
            @NotBlank String optionCode,
            @NotBlank String optionName,
            @NotBlank String optionValue
    ) {
    }

    public record CreateProductOptionCombination(
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
            @NotBlank String optionManagementCode
    ) {
    }

    public CreateProductParam toDto() {
        return new CreateProductParam(
                categoryId,
                productCode,
                productName,
                modelName,
                originalPrice,
                sellingPrice,
                description,
                options
                        .stream()
                        .map(option -> new CreateProductOptionParam(
                                option.optionCode(),
                                option.optionName(),
                                option.optionValue()
                        ))
                        .toList(),
                optionCombinations
                        .stream()
                        .map(combination -> new CreateProductOptionCombinationParam(
                                combination.optionCode1(),
                                combination.optionName1(),
                                combination.optionValue1(),
                                combination.optionCode2(),
                                combination.optionName2(),
                                combination.optionValue2(),
                                combination.optionCode3(),
                                combination.optionName3(),
                                combination.optionValue3(),
                                combination.optionCode4(),
                                combination.optionName4(),
                                combination.optionValue4(),
                                combination.optionCode5(),
                                combination.optionName5(),
                                combination.optionValue5(),
                                combination.stockQuantity(),
                                combination.optionManagementCode()
                        ))
                        .toList()
        );
    }
}
