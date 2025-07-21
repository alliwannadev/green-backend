package alliwannadev.shop.core.api.user.controller.dto.response;

import alliwannadev.shop.core.application.user.service.dto.GetUserResult;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class GetUsersMeResponseV1 {

    private Long userId;

    private String email;

    private String name;

    private String phone;

    private String imageUrl;

    @Builder(access = AccessLevel.PRIVATE)
    private GetUsersMeResponseV1(
            Long userId,
            String email,
            String name,
            String phone,
            String imageUrl
    ) {
        this.userId = userId;
        this.email = email;
        this.name = name;
        this.phone = phone;
        this.imageUrl = imageUrl;
    }

    public static GetUsersMeResponseV1 fromDto(
            GetUserResult user
    ) {
        return GetUsersMeResponseV1.builder()
                .userId(user.userId())
                .email(user.email())
                .name(user.name())
                .phone(user.phone())
                .imageUrl(user.imageUrl())
                .build();
    }
}
