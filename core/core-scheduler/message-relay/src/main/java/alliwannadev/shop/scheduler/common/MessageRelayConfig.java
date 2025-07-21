package alliwannadev.shop.scheduler.common;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@EnableScheduling
@Configuration
public class MessageRelayConfig {

    @Bean
    public Executor messageRelayExecutor() {
        return Executors.newSingleThreadScheduledExecutor();
    }
}
