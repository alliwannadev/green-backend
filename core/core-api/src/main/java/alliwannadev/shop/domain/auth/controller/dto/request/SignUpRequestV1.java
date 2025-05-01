package alliwannadev.shop.domain.auth.controller.dto.request;

import alliwannadev.shop.common.security.Role;
import alliwannadev.shop.domain.user.domain.User;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Set;

@Getter
@NoArgsConstructor
public class SignUpRequestV1 {

    @NotBlank
    private String email;

    @NotBlank
    private String password;

    @NotBlank
    private String name;

    @NotBlank
    private String phone;

    @JsonCreator
    public SignUpRequestV1(
            @JsonProperty("email") String email,
            @JsonProperty("password") String password,
            @JsonProperty("name") String name,
            @JsonProperty("phone") String phone
    ) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.phone = phone;
    }

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
