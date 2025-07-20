package alliwannadev.shop.core.application.modules.auth;

import alliwannadev.shop.core.jpa.user.domain.User;
import alliwannadev.shop.core.jpa.user.repository.UserRepository;
import alliwannadev.shop.core.application.modules.user.service.UserCacheService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class CustomUserDetailsService implements UserDetailsService {

    private final UserCacheService userCacheService;

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> foundUser = userCacheService.getOneByEmail(username);
        if (foundUser.isEmpty()) {
            System.out.println("RDB 실행됨");
            foundUser = userRepository.findOneByEmail(username);
        }

        return foundUser
                .map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException(
                        String.format(
                                "회원 정보(Email: %s)를 찾을 수 없습니다.",
                                username
                        )
                ));
    }

    private UserDetails createUserDetails(User user) {
        return new CustomUser(
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
