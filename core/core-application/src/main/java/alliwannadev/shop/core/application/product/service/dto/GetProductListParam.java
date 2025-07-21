package alliwannadev.shop.core.application.product.service.dto;

import alliwannadev.shop.core.domain.common.constant.ProductOptionCode;
import alliwannadev.shop.core.jpa.product.repository.dto.GetProductListCond;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public record GetProductListParam(
        String categoryPath,
        List<String> colors,
        List<String> sizes,
        PriceRange priceRange,
        Boolean isInStock
) {

    public record PriceRange(
            Long minPrice,
            Long maxPrice
    ) {
    }

    public GetProductListCond toCondition(String categoryPath) {
        return new GetProductListCond(
                categoryPath,
                toOptionCondList(ProductOptionCode.COLOR, colors),
                toOptionCondList(ProductOptionCode.SIZE, sizes),
                ObjectUtils.isEmpty(priceRange) ?
                        null :
                        new GetProductListCond.PriceRange(
                                priceRange.minPrice == null ? Long.MIN_VALUE : priceRange.minPrice,
                                priceRange.maxPrice == null ? Long.MAX_VALUE : priceRange.maxPrice
                        ),
                isInStock
        );
    }

    private List<GetProductListCond.OptionCond> toOptionCondList(
            ProductOptionCode code,
            List<String> optionValues
    ) {
        if (ObjectUtils.isEmpty(optionValues)) {
            return List.of();
        }

        return optionValues.stream()
                .map(optionValue -> new GetProductListCond.OptionCond(code.getCode(), optionValue))
                .toList();
    }
}
