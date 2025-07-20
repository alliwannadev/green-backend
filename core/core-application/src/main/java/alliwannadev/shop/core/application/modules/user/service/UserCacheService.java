package alliwannadev.shop.core.application.modules.user.service;

import alliwannadev.shop.core.jpa.user.model.UserEntity;
import alliwannadev.shop.core.jpa.user.repository.UserJpaRepository;
import alliwannadev.shop.core.redis.user.UserRedisRepository;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserCacheService {

    private final UserJpaRepository userJpaRepository;
    private final UserRedisRepository userRedisRepository;

    public void create(String email) {
        UserEntity userEntity = userJpaRepository
                .findOneByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));
        userRedisRepository.create(
                userEntity.toDomain(),
                Duration.ofHours(1)
        );
    }

    public Optional<UserEntity> getOneByEmail(String email) {
        return userRedisRepository
                        .getOneByEmail(email)
                        .map(UserEntity::fromDomain);
    }
}
