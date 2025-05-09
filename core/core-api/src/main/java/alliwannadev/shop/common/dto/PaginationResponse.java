package alliwannadev.shop.common.dto;

import org.springframework.data.domain.Page;

import java.util.List;

public record PaginationResponse<T>(
        List<T> content,
        int page,
        int itemsCountPerPage,
        long totalItemsCount
) {

    public static <T> PaginationResponse<T> of(
            Page<T> page,
            PaginationRequest paginationRequest
    ) {
        return new PaginationResponse<>(
                page.getContent(),
                paginationRequest.page(),
                paginationRequest.size(),
                page.getTotalElements()
        );
    }
}
