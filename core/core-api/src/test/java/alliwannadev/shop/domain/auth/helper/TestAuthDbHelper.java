package alliwannadev.shop.domain.auth.helper;

import alliwannadev.shop.common.dto.TokenInfo;
import alliwannadev.shop.domain.auth.controller.dto.request.SignInRequestV1;
import alliwannadev.shop.domain.auth.controller.dto.request.SignUpRequestV1;
import alliwannadev.shop.domain.auth.service.AuthService;
import alliwannadev.shop.domain.user.domain.User;
import alliwannadev.shop.domain.user.helper.TestUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Component
public class TestAuthDbHelper {

    private static final String DEFAULT_TEST_USER_EMAIL = "defaultuser@test.com";
    private static final String DEFAULT_TEST_USER_PASSWORD = "12345678";

    private final AuthService authService;
    private final TestUserRepository testUserRepository;

    @Transactional
    public void signUp(SignUpRequestV1 signUpRequestV1) {
        authService.signUp(signUpRequestV1);
    }

    @Transactional(readOnly = true)
    public TokenInfo signIn(SignInRequestV1 signInRequestV1) {
        return authService.signIn(signInRequestV1);
    }

    @Transactional
    public User createDefaultTestUserIfNotExists() {
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
