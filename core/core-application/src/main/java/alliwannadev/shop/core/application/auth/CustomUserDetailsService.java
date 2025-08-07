package alliwannadev.shop.core.application.auth;

import alliwannadev.shop.core.jpa.user.model.UserEntity;
import alliwannadev.shop.core.jpa.user.repository.UserJpaRepository;
import alliwannadev.shop.core.application.user.service.UserCacheService;
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

    private final UserJpaRepository userJpaRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        long userId = Long.parseLong(username);
        Optional<UserEntity> foundUser = userCacheService.getOneByUserId(userId);
        if (foundUser.isEmpty()) {
            foundUser = userJpaRepository.findById(userId);
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

    private UserDetails createUserDetails(UserEntity userEntity) {
        return new CustomUser(
                userEntity.getUserId(),
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles()
                        .stream()
                        .map(role -> new SimpleGrantedAuthority("ROLE_%s".formatted(role.name())))
                        .collect(Collectors.toSet())
        );
    }
}
