package alliwannadev.shop.domain.product.repository.dto;

import java.util.List;

public record GetProductListCond(
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
}
