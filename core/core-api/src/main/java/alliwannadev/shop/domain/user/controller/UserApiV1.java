package alliwannadev.shop.domain.user.controller;

import alliwannadev.shop.common.dto.OkResponse;
import alliwannadev.shop.common.dto.PaginationRequest;
import alliwannadev.shop.common.dto.PaginationResponse;
import alliwannadev.shop.common.security.CustomUser;
import alliwannadev.shop.domain.user.controller.dto.response.GetUserResponseV1;
import alliwannadev.shop.domain.user.controller.dto.response.GetUsersMeResponseV1;
import alliwannadev.shop.domain.user.domain.User;
import alliwannadev.shop.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping(UserApiPaths.V1_USERS)
    public OkResponse<PaginationResponse<GetUserResponseV1>> getUsers(
            @RequestParam(required = false) String keyword,
            @ModelAttribute PaginationRequest paginationRequest
    ) {
        Page<GetUserResponseV1> userPage =
                userService
                        .getAll(
                                keyword,
                                paginationRequest.getPageable()
                        ).map(GetUserResponseV1::from);

        return OkResponse.of(
                PaginationResponse.of(
                        userPage,
                        paginationRequest
                )
        );
    }
}
