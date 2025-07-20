package alliwannadev.shop.core.application.modules.auth;

import alliwannadev.shop.core.domain.common.constant.TokenType;
import alliwannadev.shop.core.domain.common.dto.TokenInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@RequiredArgsConstructor
@Service
public class JwtService {

    public static final int ACCESS_TOKEN_EXPIRATION_SECONDS = 60 * 60; // 60 분 (Minutes)
    public static final long ACCESS_TOKEN_EXPIRATION_MILLISECONDS = ACCESS_TOKEN_EXPIRATION_SECONDS * 1000L;
    public static final String GRANT_TYPE = "Bearer";

    private final JwtTokenProvider jwtTokenProvider;

    public TokenInfo createTokenInfo(Authentication authentication) {
        CustomUser customUser = (CustomUser) authentication.getPrincipal();
        return createTokenInfo(customUser);
    }

    public TokenInfo createTokenInfo(CustomUser customUser) {
        String accessToken = generateAccessToken(customUser);

        return new TokenInfo(GRANT_TYPE, accessToken);
    }

    public String getEmail(
            String token,
            TokenType tokenType
    ) {
        return jwtTokenProvider.getEmail(token, tokenType);
    }

    private String generateAccessToken(CustomUser customUser) {
        long now = (new Date()).getTime();
        Date tokenExpiration = new Date(now + ACCESS_TOKEN_EXPIRATION_MILLISECONDS);
        return jwtTokenProvider.generateToken(
                customUser,
                tokenExpiration,
                TokenType.ACCESS_TOKEN
        );
    }
}
