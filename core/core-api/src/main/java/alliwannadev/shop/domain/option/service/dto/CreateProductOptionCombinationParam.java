package alliwannadev.shop.domain.option.service.dto;

import alliwannadev.shop.domain.option.model.ProductOptionCombination;
import alliwannadev.shop.domain.product.model.Product;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

    public ProductOptionCombination toEntity(Product product) {
        List<ProductOptionParam> productOptions =
                List.of(
                        new ProductOptionParam(optionCode1, optionName1, optionValue1),
                        new ProductOptionParam(optionCode2, optionName2, optionValue2),
                        new ProductOptionParam(optionCode3, optionName3, optionValue3),
                        new ProductOptionParam(optionCode4, optionName4, optionValue4),
                        new ProductOptionParam(optionCode5, optionName5, optionValue5)
                );
        String selectedOptions =
                toOptionsText(
                        productOptions,
                        option ->
                                "%s_%s_%s".formatted(
                                        option.optionName,
                                        option.optionCode,
                                        option.optionValue
                                ),
                        "/"
                );
        String optionCodeValues = toOptionsText(
                productOptions,
                option ->
                        "%s_%s".formatted(
                                option.optionCode,
                                option.optionValue
                        ),
                "_"
        );
        String combinationCode = product.getProductId() + "_" + optionCodeValues;
        char[] charsOfOptionCodeValues =
                optionCodeValues
                        .replaceAll("_", "")
                        .toCharArray();
        Arrays.sort(charsOfOptionCodeValues);

        return ProductOptionCombination.of(
                product,
                selectedOptions,
                combinationCode,
                product.getProductId() + "_" + String.valueOf(charsOfOptionCodeValues),
                optionManagementCode
        );
    }

    private static String toOptionsText(
            List<ProductOptionParam> productOptions,
            Function<ProductOptionParam, String> mapper,
            String delimiter
    ) {
        return productOptions
                .stream()
                .filter(option ->
                        StringUtils.isNotBlank(option.optionCode) &&
                        StringUtils.isNotBlank(option.optionName) &&
                        StringUtils.isNotBlank(option.optionValue)
                )
                .map(mapper)
                .collect(Collectors.joining(delimiter));
    }

    private record ProductOptionParam(
            String optionCode,
            String optionName,
            String optionValue
    ) {
    }
}
