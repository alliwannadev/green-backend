package alliwannadev.shop.core.application.modules.user.repository.redis;

import alliwannadev.shop.core.jpa.user.domain.User;
import alliwannadev.shop.support.dataserializer.DataSerializer;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.Objects;
import java.util.Optional;

@RequiredArgsConstructor
@Repository
public class UserRedisRepository {

    private final StringRedisTemplate redisTemplate;

    public void create(
            User user,
            Duration ttl
    ) {
        redisTemplate
                .opsForValue()
                .set(
                        generateKey(user.getEmail()),
                        Objects.requireNonNull(DataSerializer.serialize(user)),
                        ttl
                );
    }

    public Optional<User> getOneByEmail(String email) {
        String user = redisTemplate
                .opsForValue()
                .get(generateKey(email));

        return StringUtils.isEmpty(user) ?
                Optional.empty() :
                Optional.ofNullable(DataSerializer.deserialize(user, User.class));
    }

    private String generateKey(String email) {
        return "user:%s".formatted(email);
    }
}
