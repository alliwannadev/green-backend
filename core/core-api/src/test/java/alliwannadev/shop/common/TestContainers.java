package alliwannadev.shop.common;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class TestContainers {

    private static final String MYSQL_DATABASE_NAME = "green";
    private static final String MYSQL_USERNAME = "green_user";
    private static final String MYSQL_PASSWORD = "123456";

    @Container
    static MySQLContainer<?> MYSQL_CONTAINER =
            new MySQLContainer<>("mysql:8.4.5")
                    .withConfigurationOverride("testcontainers/conf")
                    .withUsername(MYSQL_USERNAME)
                    .withPassword(MYSQL_PASSWORD)
                    .withDatabaseName(MYSQL_DATABASE_NAME)
                    .withInitScript("testcontainers/sql/init.sql");

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        // MySQL
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");
    }
}
