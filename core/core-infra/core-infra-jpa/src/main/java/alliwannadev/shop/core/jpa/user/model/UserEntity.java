package alliwannadev.shop.core.jpa.user.model;

import alliwannadev.shop.core.domain.common.constant.Role;
import alliwannadev.shop.core.domain.user.User;
import alliwannadev.shop.core.jpa.common.model.BaseTimeEntity;
import alliwannadev.shop.core.jpa.common.util.RolesConverter;
import alliwannadev.shop.support.snowflake.GeneratedSnowflake;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Getter
@EqualsAndHashCode(of = "userId", callSuper = false)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class UserEntity extends BaseTimeEntity {

    @Id @GeneratedSnowflake
    private Long userId;

    @Column(nullable = false, length = 100)
    private String email;

    @Column(nullable = false, length = 100)
    private String password;

    @Column(nullable = false, length = 20)
    private String name;

    @Column(nullable = false, length = 15)
    private String phone;

    private String imageUrl;

    @Convert(converter = RolesConverter.class)
    private Set<Role> roles = new HashSet<>();

    @Builder(access = AccessLevel.PRIVATE)
    private UserEntity(
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

    public User toDomain() {
        return User.of(
                userId,
                email,
                password,
                name,
                phone,
                imageUrl,
                roles
        );
    }

    public static UserEntity of(
            String email,
            String password,
            String name,
            String phone,
            String imageUrl,
            Set<Role> roles
    ) {
        return UserEntity.builder()
                .email(email)
                .password(password)
                .name(name)
                .phone(phone)
                .imageUrl(imageUrl)
                .roles(roles)
                .build();
    }

    public static UserEntity fromDomain(
            User user
    ) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .password(user.getPassword())
                .name(user.getName())
                .phone(user.getPhone())
                .imageUrl(user.getImageUrl())
                .roles(user.getRoles())
                .build();
    }

    public void updatePassword(String password) {
        this.password = password;
    }
}
