package alliwannadev.shop.core.application.warehousing.service.dto;

public record CreateWarehousingParam(
        Long productOptionCombinationId,
        String warehousingDate,
        Long quantity
) {
}
