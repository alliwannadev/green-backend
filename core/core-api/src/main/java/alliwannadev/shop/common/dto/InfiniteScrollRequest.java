package alliwannadev.shop.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record InfiniteScrollRequest(
        @NotNull @Min(1) Integer pageSize,
        Long lastCursor
) {

    public InfiniteScrollCond toCond() {
        return new InfiniteScrollCond(
                pageSize,
                lastCursor
        );
    }
}
