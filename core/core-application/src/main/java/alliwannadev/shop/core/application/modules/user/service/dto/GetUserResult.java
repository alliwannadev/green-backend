package alliwannadev.shop.core.application.modules.user.service.dto;

import alliwannadev.shop.core.domain.common.constant.Role;
import alliwannadev.shop.core.jpa.user.domain.User;
import alliwannadev.shop.core.jpa.user.repository.dto.GetUserDto;

import java.util.Set;

public record GetUserResult(
        Long userId,
        String email,
        String password,
        String name,
        String phone,
        String imageUrl,
        Set<Role> roles
) {

    public static GetUserResult from(GetUserDto dto) {
        return new GetUserResult(
                dto.userId(),
                dto.email(),
                dto.password(),
                dto.name(),
                dto.phone(),
                dto.imageUrl(),
                dto.roles()
        );
    }

    public static GetUserResult from(User user) {
        return new GetUserResult(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getName(),
                user.getPhone(),
                user.getImageUrl(),
                user.getRoles()
        );
    }
}
