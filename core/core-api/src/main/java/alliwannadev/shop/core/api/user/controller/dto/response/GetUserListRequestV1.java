package alliwannadev.shop.core.api.user.controller.dto.response;

public record GetUserListRequestV1(
    String searchType,
    String keyword
) {
}
