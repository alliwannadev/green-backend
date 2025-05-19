package alliwannadev.shop.domain.user.repository.dto;

import alliwannadev.shop.common.security.Role;
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
