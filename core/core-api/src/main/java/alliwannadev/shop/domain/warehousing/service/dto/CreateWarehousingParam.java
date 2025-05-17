package alliwannadev.shop.domain.warehousing.service.dto;

public record CreateWarehousingParam(
        Long productOptionCombinationId,
        String warehousingDate,
        Long quantity
) {
}
