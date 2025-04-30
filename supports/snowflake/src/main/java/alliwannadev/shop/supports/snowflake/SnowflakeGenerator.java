package alliwannadev.shop.supports.snowflake;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.IdentifierGenerator;
import org.springframework.stereotype.Component;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Component
public class SnowflakeGenerator implements IdentifierGenerator {

    @Override
    public Object generate(
            SharedSessionContractImplementor sharedSessionContractImplementor,
            Object o
    ) {
        // TODO: dataCenterId 및 nodeId를 주입 받아서 사용 하도록 코드 변경하기
        Snowflake snowflake = new Snowflake(1L, 1L);
        return snowflake.nextId();
    }
}
