package alliwannadev.shop.core.api.auth.controller.dto.request;

import alliwannadev.shop.core.application.auth.dto.SignUpParam;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    public SignUpParam toDto() {
        return new SignUpParam(
                email,
                password,
                name,
                phone
        );
    }
}
