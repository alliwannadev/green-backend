package alliwannadev.shop.core.domain;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = "alliwannadev.shop")
@EnableJpaRepositories(basePackages = "alliwannadev.shop")
@ComponentScan("alliwannadev.shop.core.domain")
@Configuration
public class CoreDomainConfig {

}
