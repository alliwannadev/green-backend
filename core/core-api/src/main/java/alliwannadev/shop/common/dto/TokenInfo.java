package alliwannadev.shop.common.dto;

public record TokenInfo(
        String grantType,
        String accessToken
) {
}
