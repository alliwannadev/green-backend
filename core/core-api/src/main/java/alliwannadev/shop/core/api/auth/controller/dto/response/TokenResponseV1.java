package alliwannadev.shop.core.api.auth.controller.dto.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class TokenResponseV1 {

    private String grantType;

    private String accessToken;

    @Builder(access = AccessLevel.PRIVATE)
    private TokenResponseV1(
            String grantType,
            String accessToken
    ) {
        this.grantType = grantType;
        this.accessToken = accessToken;
    }

    public static TokenResponseV1 of(
            String grantType,
            String accessToken
    ) {
        return TokenResponseV1.builder()
                .grantType(grantType)
                .accessToken(accessToken)
                .build();
    }
}
