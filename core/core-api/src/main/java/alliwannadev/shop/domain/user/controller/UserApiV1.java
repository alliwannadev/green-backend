package alliwannadev.shop.domain.user.controller;

import alliwannadev.shop.common.dto.OkResponse;
import alliwannadev.shop.common.security.CustomUser;
import alliwannadev.shop.domain.user.controller.dto.response.GetUsersMeResponseV1;
import alliwannadev.shop.domain.user.domain.User;
import alliwannadev.shop.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class UserApiV1 {

    private final UserService userService;

    @GetMapping(UserApiPaths.V1_USERS_ME)
    public OkResponse<GetUsersMeResponseV1> getUsersMe(
            @AuthenticationPrincipal CustomUser customUser
    ) {
        User findUser = userService.getOneByUserId(customUser.getUserId());
        return OkResponse.of(
                GetUsersMeResponseV1.fromEntity(findUser)
        );
    }
}
