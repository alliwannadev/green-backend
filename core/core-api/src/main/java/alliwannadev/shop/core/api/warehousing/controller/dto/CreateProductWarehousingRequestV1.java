package alliwannadev.shop.core.api.warehousing.controller.dto;

import alliwannadev.shop.core.application.warehousing.service.dto.CreateWarehousingParam;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateProductWarehousingRequestV1(
        @Positive Long productOptionCombinationId,
        @NotBlank String warehousingDate,
        @Positive Long quantity
) {

    public CreateWarehousingParam toDto() {
        return new CreateWarehousingParam(
                productOptionCombinationId,
                warehousingDate,
                quantity
        );
    }
}
