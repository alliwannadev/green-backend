package alliwannadev.shop.support.snowflake;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("alliwannadev.shop.support.snowflake")
@RequiredArgsConstructor
@Configuration
public class SnowflakeConfig {

    private final NodeSequenceRedisRepository nodeSequenceRedisRepository;

    @Bean
    public Snowflake snowflake() {
        return new Snowflake(getNextNodeId() % Snowflake.NODE_COUNT);
    }

    private Long getNextNodeId() {
        return nodeSequenceRedisRepository.getNextNodeSequence("core");
    }
}
