package alliwannadev.shop.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "alliwannadev.shop")
@EnableJpaRepositories(basePackages = "alliwannadev.shop")
@SpringBootApplication
public class MessageRelayApplication {

    public static void main(String[] args) {
        SpringApplication.run(MessageRelayApplication.class, args);
    }

}
