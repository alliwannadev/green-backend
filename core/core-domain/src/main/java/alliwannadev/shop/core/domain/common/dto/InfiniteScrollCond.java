package alliwannadev.shop.core.domain.common.dto;

public record InfiniteScrollCond(
        Integer pageSize,
        Long lastCursor
) {

    public Integer getPageSizeWithOneAdded() {
        return pageSize + 1;
    }
}
