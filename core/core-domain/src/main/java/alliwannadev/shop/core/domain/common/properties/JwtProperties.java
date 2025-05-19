package alliwannadev.shop.core.domain.common.properties;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.ConstructorBinding;
import org.springframework.validation.annotation.Validated;

@Getter
@Validated
@ConfigurationProperties(prefix = "jwt")
public class JwtProperties {

    @NotEmpty
    private final String accessSecret;

    @ConstructorBinding
    public JwtProperties(
            String accessSecret
    ) {
        this.accessSecret = accessSecret;
    }
}
