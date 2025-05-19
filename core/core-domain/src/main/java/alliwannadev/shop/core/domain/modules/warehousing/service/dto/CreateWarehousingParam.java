package alliwannadev.shop.core.domain.modules.warehousing.service.dto;

public record CreateWarehousingParam(
        Long productOptionCombinationId,
        String warehousingDate,
        Long quantity
) {
}
