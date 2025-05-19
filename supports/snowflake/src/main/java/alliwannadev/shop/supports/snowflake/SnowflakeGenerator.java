package alliwannadev.shop.supports.snowflake;

import lombok.RequiredArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SnowflakeGenerator implements IdentifierGenerator {

    private final Snowflake snowflake;

    @Override
    public Object generate(
            SharedSessionContractImplementor sharedSessionContractImplementor,
            Object o
    ) {
        return snowflake.nextId();
    }
}
