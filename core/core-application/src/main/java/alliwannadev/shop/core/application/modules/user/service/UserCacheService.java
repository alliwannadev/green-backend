package alliwannadev.shop.core.application.modules.user.service;

import alliwannadev.shop.core.jpa.user.domain.User;
import alliwannadev.shop.core.application.modules.user.repository.redis.UserRedisRepository;
import alliwannadev.shop.core.jpa.user.repository.UserRepository;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserCacheService {

    private final UserRepository userRepository;
    private final UserRedisRepository userRedisRepository;

    public void create(String email) {
        User user = userRepository
                .findOneByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        userRedisRepository.create(user, Duration.ofHours(1));
    }

    public Optional<User> getOneByEmail(String email) {
        return userRedisRepository.getOneByEmail(email);
    }
}
