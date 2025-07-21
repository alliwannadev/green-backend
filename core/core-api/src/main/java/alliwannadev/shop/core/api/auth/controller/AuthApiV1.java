package alliwannadev.shop.core.api.auth.controller;

import alliwannadev.shop.core.api.common.dto.OkResponse;
import alliwannadev.shop.core.domain.common.dto.TokenInfo;
import alliwannadev.shop.core.api.auth.controller.dto.request.SignInRequestV1;
import alliwannadev.shop.core.api.auth.controller.dto.request.SignUpRequestV1;
import alliwannadev.shop.core.api.auth.controller.dto.response.TokenResponseV1;
import alliwannadev.shop.core.application.auth.AuthService;
import alliwannadev.shop.core.application.user.service.UserCacheService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
public class AuthApiV1 {

    private final AuthService authService;
    private final UserCacheService userCacheService;

    @PostMapping(AuthApiPaths.V1_SIGN_UP)
    public OkResponse<Void> signUp(
            @Valid @RequestBody SignUpRequestV1 signUpRequestV1
    ) {
        authService.signUp(signUpRequestV1.toDto());
        return OkResponse.of("회원가입을 완료했습니다.");
    }

    @PostMapping(AuthApiPaths.V1_SIGN_IN)
    public OkResponse<TokenResponseV1> signIn(
            @Valid @RequestBody SignInRequestV1 signInRequestV1
    ) {
        TokenInfo tokenInfo = authService.signIn(signInRequestV1.toDto());
        userCacheService.create(authService.getEmailFromToken(tokenInfo));

        return OkResponse.of(
                TokenResponseV1.of(
                        tokenInfo.grantType(),
                        tokenInfo.accessToken()
                )
        );
    }
}
