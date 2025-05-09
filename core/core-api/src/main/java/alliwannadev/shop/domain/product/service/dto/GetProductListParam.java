package alliwannadev.shop.domain.product.service.dto;

import alliwannadev.shop.domain.product.repository.dto.GetProductListCond;
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
                colors,
                sizes,
                ObjectUtils.isEmpty(priceRange) ? null :
                        new GetProductListCond.PriceRange(
                                priceRange.minPrice,
                                priceRange.maxPrice
                        ),
                isInStock
        );
    }
}
