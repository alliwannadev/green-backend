package alliwannadev.shop.api.auth.support;

import alliwannadev.shop.core.domain.common.dto.TokenInfo;
import alliwannadev.shop.api.auth.controller.dto.request.SignInRequestV1;
import alliwannadev.shop.api.auth.controller.dto.request.SignUpRequestV1;
import alliwannadev.shop.core.application.modules.auth.AuthService;
import alliwannadev.shop.api.user.support.TestUserRepository;
import alliwannadev.shop.core.jpa.user.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class TestAuthDbUtil {

    private static final String DEFAULT_TEST_USER_EMAIL = "defaultuser@test.com";
    private static final String DEFAULT_TEST_USER_PASSWORD = "12345678";

    private final AuthService authService;
    private final TestUserRepository testUserRepository;

    @Transactional
    public void signUp(SignUpRequestV1 signUpRequestV1) {
        authService.signUp(signUpRequestV1.toDto());
    }

    @Transactional(readOnly = true)
    public TokenInfo signIn(SignInRequestV1 signInRequestV1) {
        return authService.signIn(signInRequestV1.toDto());
    }

    @Transactional
    public UserEntity createDefaultTestUserIfNotExists() {
        if (testUserRepository.existsByEmail(DEFAULT_TEST_USER_EMAIL)) {
            return testUserRepository
                    .findOneByEmail(DEFAULT_TEST_USER_EMAIL)
                    .orElseThrow();
        }

        SignUpRequestV1 signUpRequestV1 =
                new SignUpRequestV1(
                        DEFAULT_TEST_USER_EMAIL,
                        DEFAULT_TEST_USER_PASSWORD,
                        "defaultUser",
                        "01077779999"
                );
        signUp(signUpRequestV1);

        return testUserRepository
                .findOneByEmail(DEFAULT_TEST_USER_EMAIL)
                .orElseThrow();
    }

    @Transactional
    public String getDefaultToken() {
        createDefaultTestUserIfNotExists();
        SignInRequestV1 signInRequestV1 =
                new SignInRequestV1(
                        DEFAULT_TEST_USER_EMAIL,
                        DEFAULT_TEST_USER_PASSWORD
                );
        TokenInfo tokenInfo = signIn(signInRequestV1);
        
        return "%s %s".formatted(
                tokenInfo.grantType(),
                tokenInfo.accessToken()
        );
    }
}
