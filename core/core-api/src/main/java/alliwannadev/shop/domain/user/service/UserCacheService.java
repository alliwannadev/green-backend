package alliwannadev.shop.domain.user.service;

import alliwannadev.shop.common.error.BusinessException;
import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.domain.user.domain.User;
import alliwannadev.shop.domain.user.repository.UserRedisRepository;
import alliwannadev.shop.domain.user.repository.UserRepository;
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
