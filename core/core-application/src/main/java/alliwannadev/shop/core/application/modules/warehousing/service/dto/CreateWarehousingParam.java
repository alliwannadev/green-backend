package alliwannadev.shop.core.application.modules.warehousing.service.dto;

public record CreateWarehousingParam(
        Long productOptionCombinationId,
        String warehousingDate,
        Long quantity
) {
}
