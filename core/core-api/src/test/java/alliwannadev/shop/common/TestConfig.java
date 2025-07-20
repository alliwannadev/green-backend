package alliwannadev.shop.common;

import alliwannadev.shop.supports.snowflake.Snowflake;
import alliwannadev.shop.supports.snowflake.SnowflakeGenerator;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class TestConfig {

    @Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
        return new JPAQueryFactory(entityManager);
    }

    @Bean
    public Snowflake snowflake() {
        return new Snowflake(1L);
    }

    @Bean
    public SnowflakeGenerator snowflakeGenerator() {
        return new SnowflakeGenerator(snowflake());
    }
}
