package alliwannadev.shop.supports.snowflake;

import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("단위 테스트 - Snowflake")
@Slf4j
class SnowflakeTest {

    @DisplayName("[SUPPORTS] Snowflake.nextId()는 중복 없이 순차적인 ID가 생성 되어야 한다.")
    @Test
    void testNextIdOfSnowflake() {
        // given
        Snowflake snowflake = new Snowflake(1L);
        List<Future<List<Long>>> futures = new ArrayList<>();
        int requestCount = 1000;
        int idCount = 1000;

        // when
        try (ExecutorService executorService = Executors.newFixedThreadPool(10)) {
            for (int i = 0; i < requestCount; i++) {
                futures.add(executorService.submit(() -> generateIdList(snowflake, idCount)));
            }
        }

        // then
        List<Long> result = new ArrayList<>();
        for (Future<List<Long>> future : futures) {
            List<Long> idList = new ArrayList<>();
            try {
                idList = future.get();
            } catch (Exception e) {
                log.error("[SnowflakeTest.testNextIdOfSnowflake]", e);
            }

            for (int i = 1; i < idList.size(); i++) {
                long beforeId = idList.get(i - 1);
                long currentId = idList.get(i);
                Assertions.assertThat(currentId).isGreaterThan(beforeId);
            }

            result.addAll(idList);
        }
        assertThat(
                result.stream()
                        .distinct()
                        .count()
        ).isEqualTo(requestCount * idCount);
    }

    private List<Long> generateIdList(Snowflake snowflake, int count) {
        List<Long> idList = new ArrayList<>();
        while (count-- > 0) {
            idList.add(snowflake.nextId());
        }

        return idList;
    }
}
