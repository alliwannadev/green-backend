package alliwannadev.shop.common.security;

import alliwannadev.shop.domain.user.domain.User;
import alliwannadev.shop.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findOneByEmail(username)
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(
                                "회원 정보(Email: %s)를 찾을 수 없습니다.",
                                username
                        )
                ));
    }

    private UserDetails createUserDetails(User user) {
        return new alliwannadev.shop.common.security.CustomUser(
                user.getUserId(),
                user.getEmail(),
                user.getPassword(),
                user.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_%s".formatted(role.name())))
                        .collect(Collectors.toSet())
        );
    }
}
