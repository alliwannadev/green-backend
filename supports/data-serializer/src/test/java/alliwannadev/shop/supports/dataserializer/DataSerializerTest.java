package alliwannadev.shop.supports.dataserializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("단위 테스트 - DataSerializer")
@Slf4j
class DataSerializerTest {

    @DisplayName("[SUPPORTS] JSON 문자열을 역직렬화하는 경우")
    @Test
    void testJsonStringDeserialization() throws JsonProcessingException {
        // given
        SampleData sampleData = new SampleData(1L, "sample");
        ObjectMapper objectMapper = new ObjectMapper();
        String jsonString = objectMapper.writeValueAsString(sampleData);

        // when
        SampleData deserializeObject = DataSerializer.deserialize(jsonString, SampleData.class);

        // then
        assertThat(sampleData.getId()).isEqualTo(deserializeObject.getId());
        assertThat(sampleData.getName()).isEqualTo(deserializeObject.getName());
    }

    @DisplayName("[SUPPORTS] 특정 객체를 역직렬화하는 경우")
    @Test
    void testObjectDeserialization() {
        // given
        SampleData sampleData = new SampleData(1L, "sample");

        // when
        SampleData deserializeObject = DataSerializer.deserialize(sampleData, SampleData.class);

        // then
        assertThat(sampleData.getId()).isEqualTo(deserializeObject.getId());
        assertThat(sampleData.getName()).isEqualTo(deserializeObject.getName());
    }

    @DisplayName("[SUPPORTS] 특정 객체를 직렬화하는 경우")
    @Test
    void testSerialize() throws JsonProcessingException {
        // given
        SampleData sampleData = new SampleData(1L, "sample");

        // when
        String jsonString = DataSerializer.serialize(sampleData);

        // then
        ObjectMapper objectMapper = new ObjectMapper();
        String comparedJsonString = objectMapper.writeValueAsString(sampleData);
        assertThat(jsonString).isEqualTo(comparedJsonString);

    }

    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    @Getter
    static class SampleData {

        private Long id;
        private String name;

        public SampleData(Long id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
