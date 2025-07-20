package alliwannadev.shop.core.application.modules.auth.dto;

import alliwannadev.shop.core.domain.common.constant.Role;
import alliwannadev.shop.core.jpa.user.model.UserEntity;

import java.util.Set;

public record SignUpParam(
    String email,
    String password,
    String name,
    String phone
) {

    public UserEntity toUserEntity() {
        return UserEntity.of(
                email,
                password,
                name,
                phone,
                "",
                Set.of(Role.USER)
        );
    }
}
