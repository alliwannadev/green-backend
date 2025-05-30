package alliwannadev.shop.core.domain.modules.auth;

import alliwannadev.shop.core.domain.modules.auth.dto.SignInParam;
import alliwannadev.shop.core.domain.modules.auth.dto.SignUpParam;
import alliwannadev.shop.core.domain.common.dto.TokenInfo;
import alliwannadev.shop.core.domain.common.error.BusinessException;
import alliwannadev.shop.core.domain.common.error.ErrorCode;
import alliwannadev.shop.core.domain.common.security.TokenType;
import alliwannadev.shop.core.domain.modules.user.domain.User;
import alliwannadev.shop.core.domain.modules.user.service.UserService;
import alliwannadev.shop.supports.event.EventType;
import alliwannadev.shop.supports.event.payload.UserSignedUpEventPayload;
import alliwannadev.shop.supports.outbox.OutboxEventPublisher;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class AuthService {

    private final UserService userService;
    private final JwtService jwtService;
    private final OutboxEventPublisher outboxEventPublisher;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void signUp(SignUpParam signUpParam) {
        if (userService
                .getOneByEmail(signUpParam.email())
                .isPresent()
        ) {
            throw new BusinessException(ErrorCode.ALREADY_SIGNED_UP_USER);
        }

        User toBeSavedUser = signUpParam.toUserEntity();
        toBeSavedUser.updatePassword(passwordEncoder.encode(toBeSavedUser.getPassword()));
        userService.saveIfNotExists(toBeSavedUser);

        outboxEventPublisher.publish(
                EventType.USER_SIGNED_UP,
                UserSignedUpEventPayload
                        .builder()
                        .userId(toBeSavedUser.getUserId())
                        .email(toBeSavedUser.getEmail())
                        .build(),
                toBeSavedUser.getUserId()
        );
    }

    @Transactional(readOnly = true)
    public TokenInfo signIn(SignInParam signInParam) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        signInParam.email(),
                        signInParam.password()
                );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtService.createTokenInfo(authentication);
    }

    public String getEmailFromToken(TokenInfo tokenInfo) {
        return jwtService.getEmail(tokenInfo.accessToken(), TokenType.ACCESS_TOKEN);
    }
}
