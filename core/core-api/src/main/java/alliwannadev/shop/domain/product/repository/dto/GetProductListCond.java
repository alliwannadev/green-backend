package alliwannadev.shop.domain.product.repository.dto;

import java.util.List;

public record GetProductListCond(
        String categoryPath,
        List<OptionCond> colors,
        List<OptionCond> sizes,
        PriceRange priceRange,
        Boolean isInStock
) {

    public record PriceRange(
            Long minPrice,
            Long maxPrice
    ) {
    }

    public record OptionCond(
            String optionCode,
            String optionValue
    ) {
    }
}
