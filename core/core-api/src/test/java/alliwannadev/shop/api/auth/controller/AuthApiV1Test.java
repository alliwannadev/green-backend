package alliwannadev.shop.api.auth.controller;

import alliwannadev.shop.common.IntegrationTest;
import alliwannadev.shop.common.TestContainers;
import alliwannadev.shop.common.TestKafkaUtils;
import alliwannadev.shop.core.domain.common.error.ErrorCode;
import alliwannadev.shop.api.auth.controller.dto.request.SignInRequestV1;
import alliwannadev.shop.api.auth.controller.dto.request.SignUpRequestV1;
import alliwannadev.shop.api.auth.support.TestAuthDbUtil;
import alliwannadev.shop.support.dataserializer.DataSerializer;
import alliwannadev.shop.support.event.Event;
import alliwannadev.shop.support.event.EventPayload;
import alliwannadev.shop.support.event.EventType;
import alliwannadev.shop.support.event.payload.UserSignedUpEventPayload;
import alliwannadev.shop.support.outbox.MessageRelay;
import alliwannadev.shop.support.outbox.OutboxEventPublisher;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("통합 테스트 - 회원 인증 API V1")
@Transactional
@IntegrationTest
class AuthApiV1Test extends TestContainers {

    @Autowired MockMvc mockMvc;
    @Autowired TestAuthDbUtil testAuthDbUtil;

    @MockitoBean OutboxEventPublisher outboxEventPublisher;
    @Autowired MessageRelay messageRelay;

    @AfterEach
    void tearDown() {
        TestKafkaUtils.deleteTopic(EventType.USER_SIGNED_UP.getTopic());
    }

    @DisplayName("[API][POST][SUCCESS] 회원가입 API 호출 시, 유효한 파라미터를 전달하면 성공 응답을 반환한다.")
    @Test
    void willSucceedIfCallSignUpApiWithValidParams() throws Exception {
        // Given
        SignUpRequestV1 signUpRequestV1 = new SignUpRequestV1("tester@test.com", "123456", "dh", "01011112222");
        String requestBody = DataSerializer.serialize(signUpRequestV1);
        TestKafkaUtils.createTopic(EventType.USER_SIGNED_UP.getTopic(), 3, (short) 1);
        TestKafkaUtils.mockPublishingOutboxEvent(outboxEventPublisher, messageRelay);

        // When & Then
        callSignUpApiV1(requestBody)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("회원가입을 완료했습니다."));

        List<ConsumerRecord<String, String>> records =
                TestKafkaUtils.createConsumerAndGetAllRecords(
                        "coupon-consumer",
                        1
                );
        String message = records.getFirst().value();
        Event<EventPayload> event = Event.fromJson(message);
        UserSignedUpEventPayload eventPayload = (UserSignedUpEventPayload) event.getPayload();
        assertThat(event.getEventId()).isNotNull();
        assertThat(event.getEventType()).isEqualTo(EventType.USER_SIGNED_UP);
        assertThat(eventPayload.getEmail()).isEqualTo(signUpRequestV1.getEmail());
    }

    @DisplayName("[API][POST][FAIL] 회원가입 API 호출 시, 잘못된 파라미터 값을 전달하면 에러 응답을 반환한다.")
    @Test
    void willFailedIfCallSignUpApiWithInvalidParams() throws Exception {
        // Given
        SignUpRequestV1 signUpRequestV1 = new SignUpRequestV1(
                null,
                null,
                null,
                null
        );
        String requestBody = DataSerializer.serialize(signUpRequestV1);

        // When & Then
        callSignUpApiV1(requestBody)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()));

    }

    @DisplayName("[API][POST][SUCCESS] 로그인 API 호출 시, 유효한 파라미터를 전달하면 성공 응답을 반환한다.")
    @Test
    void willSucceedIfSignInApiWithNormalParams() throws Exception {
        // Given
        SignUpRequestV1 signUpRequestV1 = new SignUpRequestV1("tester@test.com", "123456", "dh", "01011112222");
        testAuthDbUtil.signUp(signUpRequestV1);

        SignInRequestV1 signInRequestV1 =
                new SignInRequestV1(
                        "tester@test.com",
                        "123456"
                );
        String requestBody = DataSerializer.serialize(signInRequestV1);

        // When & Then
        callSignInApiV1(requestBody)
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.data.grantType").value("Bearer"))
                .andExpect(jsonPath("$.data.accessToken").isNotEmpty());
    }

    @DisplayName("[API][POST][FAIL] 로그인 API 호출 시, 잘못된 파라미터를 전달하면 에러 응답을 반환한다.")
    @Test
    void willFailedIfCallSignInApiWithInvalidParams() throws Exception {
        // Given
        SignUpRequestV1 signUpRequestV1 = new SignUpRequestV1("tester@test.com", "123456", "dh", "01011112222");
        testAuthDbUtil.signUp(signUpRequestV1);

        SignInRequestV1 signInRequestV1 =
                new SignInRequestV1(
                        "tester@test.com",
                        "111111"
                );
        String requestBody = DataSerializer.serialize(signInRequestV1);

        // When & Then
        callSignInApiV1(requestBody)
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_ID_OR_PASSWORD.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_ID_OR_PASSWORD.getMessage()));
    }

    private ResultActions callSignUpApiV1(String requestBody) throws Exception {
        return mockMvc.perform(
                post(AuthApiPaths.V1_SIGN_UP)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody)
        );
    }

    private ResultActions callSignInApiV1(String requestBody) throws Exception {
        return mockMvc.perform(
                post(AuthApiPaths.V1_SIGN_IN)
                        .contentType(APPLICATION_JSON)
                        .content(requestBody)
        );
    }
}
