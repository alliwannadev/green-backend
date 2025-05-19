package alliwannadev.shop.core.domain.modules.user.repository.dto;

import alliwannadev.shop.core.domain.common.security.Role;
import com.querydsl.core.annotations.QueryProjection;

import java.util.Set;

public record GetUserDto(
    Long userId,
    String email,
    String password,
    String name,
    String phone,
    String imageUrl,
    Set<Role> roles
) {

    @QueryProjection
    public GetUserDto {
    }
}
