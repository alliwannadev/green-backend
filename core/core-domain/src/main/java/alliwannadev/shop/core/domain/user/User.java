package alliwannadev.shop.core.domain.user;

import alliwannadev.shop.core.domain.common.constant.Role;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(of = "userId", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    private Long userId;

    private String email;

    private String password;

    private String name;

    private String phone;

    private String imageUrl;

    private Set<Role> roles = new HashSet<>();

    @Builder(access = AccessLevel.PRIVATE)
    private User(
            Long userId,
            String email,
            String password,
            String name,
            String phone,
            String imageUrl,
            Set<Role> roles
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
        this.imageUrl = imageUrl;
        this.roles = roles;
    }

    public static User of(
            Long userId,
            String email,
            String password,
            String name,
            String phone,
            String imageUrl,
            Set<Role> roles
    ) {
        return User.builder()
                .userId(userId)
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .imageUrl(imageUrl)
                .roles(roles)
                .build();
    }
}
