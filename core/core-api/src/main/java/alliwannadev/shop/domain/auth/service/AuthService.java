package alliwannadev.shop.domain.auth.service;

import alliwannadev.shop.common.dto.TokenInfo;
import alliwannadev.shop.common.error.BusinessException;
import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.common.security.JwtService;
import alliwannadev.shop.domain.auth.controller.dto.request.SignInRequestV1;
import alliwannadev.shop.domain.auth.controller.dto.request.SignUpRequestV1;
import alliwannadev.shop.domain.user.domain.User;
import alliwannadev.shop.domain.user.service.UserService;
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
    public void signUp(SignUpRequestV1 signUpRequestV1) {
        if (userService
                .getOneByEmail(signUpRequestV1.getEmail())
                .isPresent()
        ) {
            throw new BusinessException(ErrorCode.ALREADY_SIGNED_UP_USER);
        }

        User toBeSavedUser = signUpRequestV1.toUserEntity();
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
    public TokenInfo signIn(SignInRequestV1 signInRequestV1) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(
                        signInRequestV1.getEmail(),
                        signInRequestV1.getPassword()
                );

        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtService.createTokenInfo(authentication);
    }
}
