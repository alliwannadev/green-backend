package alliwannadev.shop.support.snowflake;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("alliwannadev.shop.support.snowflake")
@Configuration
public class SnowflakeConfig {

    @Value("${app.node-id}")
    private Long nodeId;

    @Bean
    public Snowflake snowflake() {
        return new Snowflake(nodeId);
    }
}
