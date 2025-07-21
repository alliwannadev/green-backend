package alliwannadev.shop.core.api.common.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public record PaginationRequest(
        @NotNull @Min(1) Integer page, // 현재 페이지
        @NotNull @Min(10) Integer size // 한 페이지에 표시할 데이터 개수
) {

    public Pageable getPageable() {
        return PageRequest.of(page - 1, size);
    }

    public Pageable getPageable(Sort sort) {
        return PageRequest.of(page - 1, size, sort);
    }
}
