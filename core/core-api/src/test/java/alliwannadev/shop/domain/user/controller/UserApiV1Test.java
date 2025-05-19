package alliwannadev.shop.domain.user.controller;

import alliwannadev.shop.common.IntegrationTest;
import alliwannadev.shop.common.TestContainers;
import alliwannadev.shop.domain.auth.support.TestAuthDbUtil;
import alliwannadev.shop.core.domain.modules.user.domain.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("통합 테스트 - 회원 API V1")
@Transactional
@IntegrationTest
class UserApiV1Test extends TestContainers {

    @Autowired MockMvc mockMvc;
    @Autowired TestAuthDbUtil testAuthDbUtil;

    @DisplayName("[API][GET][SUCCESS] 회원 ME API를 호출하면 회원 본인 정보를 반환한다.")
    @Test
    void willSucceedIfCallUsersMeApiWithValidParams() throws Exception {
        // Given
        User defaultTestUser = testAuthDbUtil.createDefaultTestUserIfNotExists();
        String accessToken = testAuthDbUtil.getDefaultToken();

        // When & Then
        mockMvc
                .perform(
                        get(UserApiPaths.V1_USERS_ME)
                                .contentType(APPLICATION_JSON)
                                .header("Authorization", accessToken)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.data.userId").value(defaultTestUser.getUserId()))
                .andExpect(jsonPath("$.data.email").value(defaultTestUser.getEmail()))
                .andExpect(jsonPath("$.data.name").value(defaultTestUser.getName()))
                .andExpect(jsonPath("$.data.phone").value(defaultTestUser.getPhone()))
                .andExpect(jsonPath("$.data.imageUrl").value(defaultTestUser.getImageUrl()));
    }
}
