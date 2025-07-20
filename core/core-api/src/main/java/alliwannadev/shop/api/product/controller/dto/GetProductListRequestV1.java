package alliwannadev.shop.api.product.controller.dto;

import alliwannadev.shop.core.application.modules.product.service.dto.GetProductListParam;
import org.apache.commons.lang3.ObjectUtils;

import java.util.List;

public record GetProductListRequestV1(
        String categoryPath,
        List<String> colors,
        List<String> sizes,
        String priceRange,
        Boolean isInStock
) {

    public GetProductListParam toDto() {
        GetProductListParam.PriceRange newPriceRange;
        if (ObjectUtils.isEmpty(priceRange)) {
            newPriceRange = null;
        } else {
            String[] priceArray = priceRange.split("~");
            newPriceRange = new GetProductListParam.PriceRange(
                    Long.parseLong(priceArray[0]),
                    Long.parseLong(priceArray[1])
            );
        }

        return new GetProductListParam(
                categoryPath,
                colors,
                sizes,
                newPriceRange,
                isInStock
        );
    }
}
