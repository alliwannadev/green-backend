package alliwannadev.shop.domain.user.controller.dto.response;

import alliwannadev.shop.common.security.Role;
import alliwannadev.shop.domain.user.service.dto.GetUserResult;

import java.util.Set;

public record GetUserResponseV1(
    Long userId,
    String email,
    String password,
    String name,
    String phone,
    String imageUrl,
    Set<Role> roles
) {

    public static GetUserResponseV1 from(GetUserResult result) {
        return new GetUserResponseV1(
                result.userId(),
                result.email(),
                result.password(),
                result.name(),
                result.phone(),
                result.imageUrl(),
                result.roles()
        );
    }
}
