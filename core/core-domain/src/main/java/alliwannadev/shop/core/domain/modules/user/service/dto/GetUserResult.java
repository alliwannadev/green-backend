package alliwannadev.shop.core.domain.modules.user.service.dto;

import alliwannadev.shop.core.domain.common.security.Role;
import alliwannadev.shop.core.domain.modules.user.repository.dto.GetUserDto;

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
}
