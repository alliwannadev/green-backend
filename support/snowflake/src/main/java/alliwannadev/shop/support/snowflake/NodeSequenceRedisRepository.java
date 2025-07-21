package alliwannadev.shop.support.snowflake;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class NodeSequenceRedisRepository {

    private final StringRedisTemplate redisTemplate;

    public Long getNextNodeSequence(String appPrefix) {
        return redisTemplate
                .opsForValue()
                .increment(generateKey(appPrefix));
    }

    private String generateKey(String appPrefix) {
        return "%s:node-sequence".formatted(appPrefix);
    }
}
