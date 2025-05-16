package alliwannadev.shop.common.dto;

import java.util.List;

public record InfiniteScrollResponse<T>(
        List<T> content,
        Boolean hasNext
) {

    public static <T> InfiniteScrollResponse<T> of(
            List<T> content,
            int pageSize
    ) {
        List<T> limited = content
                .stream()
                .limit(pageSize)
                .toList();

        return new InfiniteScrollResponse<>(
                limited,
                content.size() > pageSize
        );
    }
}
