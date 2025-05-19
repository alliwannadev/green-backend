package alliwannadev.shop.core.domain.common.dto;

public record TokenInfo(
        String grantType,
        String accessToken
) {
}
