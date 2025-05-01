package alliwannadev.shop.common.security;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
public class CustomUser extends User {

    private final String email;

    public CustomUser(
            Long userId,
            String email,
            String password,
            Collection<? extends GrantedAuthority> authorities
    ) {
        super(String.valueOf(userId), password, authorities);
        this.email = email;
    }

    public Long getUserId() {
        return Long.parseLong(getUsername());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName()).append(" [");
        sb.append("Username=").append(getUsername()).append(", ");
        sb.append("Email=").append(getEmail()).append(", ");
        sb.append("Password=[PROTECTED], ");
        sb.append("Granted Authorities=").append(getAuthorities()).append("]");

        return sb.toString();
    }
}
