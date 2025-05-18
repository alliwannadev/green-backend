package alliwannadev.shop.common;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.kafka.KafkaContainer;

public abstract class TestContainers {

    private static final String MYSQL_DATABASE_NAME = "green";
    private static final String MYSQL_USERNAME = "green_user";
    private static final String MYSQL_PASSWORD = "123456";

    public static final int REDIS_PORT = 6379;

    static MySQLContainer<?> MYSQL_CONTAINER =
            new MySQLContainer<>("mysql:8.4.5")
                    .withConfigurationOverride("testcontainers/conf")
                    .withUsername(MYSQL_USERNAME)
                    .withPassword(MYSQL_PASSWORD)
                    .withDatabaseName(MYSQL_DATABASE_NAME)
                    .withInitScript("testcontainers/sql/init.sql")
                    .withReuse(true);

    static GenericContainer<?> REDIS_CONTAINER =
            new GenericContainer<>("redis:7.4.3")
                    .withExposedPorts(REDIS_PORT)
                    .withReuse(true);

    public static KafkaContainer KAFKA_CONTAINER =
            new KafkaContainer("apache/kafka:3.8.0")
                    .withReuse(true);

    static {
        MYSQL_CONTAINER.start();
        REDIS_CONTAINER.start();
        KAFKA_CONTAINER.start();
    }

    @DynamicPropertySource
    static void overrideProps(DynamicPropertyRegistry registry) {
        // MySQL
        registry.add("spring.datasource.url", MYSQL_CONTAINER::getJdbcUrl);
        registry.add("spring.datasource.username", MYSQL_CONTAINER::getUsername);
        registry.add("spring.datasource.password", MYSQL_CONTAINER::getPassword);
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "none");

        // Redis
        registry.add("spring.data.redis.host", REDIS_CONTAINER::getHost);
        registry.add("spring.data.redis.port", () -> REDIS_CONTAINER.getMappedPort(REDIS_PORT).toString());

        // Kafka
        registry.add("spring.kafka.bootstrap-servers", KAFKA_CONTAINER::getBootstrapServers);
    }
}
