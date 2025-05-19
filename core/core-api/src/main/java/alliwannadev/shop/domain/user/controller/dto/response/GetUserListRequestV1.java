package alliwannadev.shop.domain.user.controller.dto.response;

public record GetUserListRequestV1(
    String searchType,
    String keyword
) {
}
