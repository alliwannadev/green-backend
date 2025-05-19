package alliwannadev.shop.core.domain.modules.auth.dto;

import alliwannadev.shop.core.domain.common.security.Role;
import alliwannadev.shop.core.domain.modules.user.domain.User;

import java.util.Set;

public record SignUpParam(
    String email,
    String password,
    String name,
    String phone
) {

    public User toUserEntity() {
        return User.of(
                email,
                password,
                name,
                phone,
                "",
                Set.of(Role.USER)
        );
    }
}
