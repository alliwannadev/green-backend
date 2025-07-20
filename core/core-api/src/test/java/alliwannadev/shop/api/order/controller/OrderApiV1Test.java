package alliwannadev.shop.api.order.controller;

import alliwannadev.shop.common.IntegrationTest;
import alliwannadev.shop.common.TestContainers;
import alliwannadev.shop.api.auth.support.TestAuthDbUtil;
import alliwannadev.shop.core.application.modules.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.core.application.modules.option.service.dto.CreateProductOptionParam;
import alliwannadev.shop.api.option.support.TestProductOptionCombinationDbUtil;
import alliwannadev.shop.api.order.controller.dto.CreateOrderRequestV1;
import alliwannadev.shop.core.application.modules.product.service.dto.CreateProductParam;
import alliwannadev.shop.api.product.support.TestProductDbUtil;
import alliwannadev.shop.api.warehousing.support.TestProductWarehousingDbUtil;
import alliwannadev.shop.core.jpa.option.model.ProductOptionCombinationEntity;
import alliwannadev.shop.core.jpa.product.model.ProductEntity;
import alliwannadev.shop.support.dataserializer.DataSerializer;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("통합 테스트 - 주문 API V1")
@Transactional
@IntegrationTest
class OrderApiV1Test extends TestContainers {

    @Autowired MockMvc mockMvc;
    @Autowired TestAuthDbUtil testAuthDbUtil;
    @Autowired TestProductDbUtil testProductDbUtil;
    @Autowired TestProductOptionCombinationDbUtil testProductOptionCombinationDbUtil;
    @Autowired TestProductWarehousingDbUtil testProductWarehousingDbUtil;

    @DisplayName("[API][POST][SUCCESS] 주문 등록 API 호출")
    @Test
    void givenOrderParameters_whenCreateOrder_thenReturnSuccessfulResponse() throws Exception {
        // Given
        testAuthDbUtil.createDefaultTestUserIfNotExists();
        String accessToken = testAuthDbUtil.getDefaultToken();
        ProductEntity product = createProduct();
        Long quantity = 2L;
        ProductOptionCombinationEntity productOptionCombination =
                testProductOptionCombinationDbUtil
                        .getByCond(
                                product.getProductId(),
                                "색상_COLOR_BLACK/사이즈_SIZE_M"
                        )
                        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_COMBINATION_NOT_FOUND));
        testProductWarehousingDbUtil.create(
                productOptionCombination.getProductOptionCombinationId(),
                "20250518",
                quantity
        );

        List<CreateOrderRequestV1.OrderItem> requestOrderItemList =
                List.of(getCreateOrderItem(product, productOptionCombination, quantity));

        CreateOrderRequestV1 createOrderRequestV1 =
                new CreateOrderRequestV1(
                        "테스터",
                        "01011112222",
                        "서울특별시 OO구 OO동 000-00",
                        "111-11",
                        quantity,
                        product.getSellingPrice() * quantity,
                        0L,
                        product.getSellingPrice() * quantity,
                        requestOrderItemList
                );
        String requestBody = DataSerializer.serialize(createOrderRequestV1);

        // When & Then
        mockMvc.perform(
                        post(OrderApiPaths.V1_ORDERS)
                                .header(
                                        HttpHeaders.AUTHORIZATION,
                                        accessToken
                                )
                                .contentType(APPLICATION_JSON)
                                .content(requestBody)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("API 요청을 완료하였습니다."));
    }

    @DisplayName("[API][POST][FAIL] 주문 등록 API 호출 - 잘못된 파라미터를 전달하는 경우")
    @Test
    void givenInvalidOrderParameters_whenCreateOrder_thenReturnFailedResponse() throws Exception {
        // Given
        testAuthDbUtil.createDefaultTestUserIfNotExists();
        String accessToken = testAuthDbUtil.getDefaultToken();
        CreateOrderRequestV1 createOrderRequestV1 =
                new CreateOrderRequestV1(
                        "",
                        "",
                        "",
                        "",
                        0L,
                        0L,
                        0L,
                        0L,
                        new ArrayList<>()
                );
        String requestBody = DataSerializer.serialize(createOrderRequestV1);

        // When & Then
        mockMvc.perform(
                        post(OrderApiPaths.V1_ORDERS)
                                .header(
                                        HttpHeaders.AUTHORIZATION,
                                        accessToken
                                )
                                .contentType(APPLICATION_JSON)
                                .content(requestBody)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

    private ProductEntity createProduct() {
        CreateProductParam createProductParam = new CreateProductParam(
                1L,
                "NIKE-T-SHIRT-001",
                "나이키 티셔츠",
                "MODEL-NAME-001",
                100L,
                150L,
                "나이키 티셔츠 입니다.",
                List.of(
                        new CreateProductOptionParam("COLOR", "색상", "BLACK"),
                        new CreateProductOptionParam("SIZE", "사이즈", "M")
                ),
                List.of(
                        new CreateProductOptionCombinationParam("COLOR", "색상", "BLACK", "SIZE", "사이즈", "M", "", "", "", "", "", "", "", "", "", 10L, "COLOR-BLACK/SIZE-M")
                )
        );

        return testProductDbUtil.createProduct(createProductParam);
    }

    private CreateOrderRequestV1.OrderItem getCreateOrderItem(
            ProductEntity product,
            ProductOptionCombinationEntity productOptionCombination,
            Long quantity
    ) {
        return new CreateOrderRequestV1.OrderItem(
                product.getProductId(),
                productOptionCombination.getProductOptionCombinationId(),
                product.getSellingPrice(),
                0L,
                product.getSellingPrice(),
                quantity,
                product.getSellingPrice() * quantity,
                0L,
                product.getSellingPrice() * quantity
        );
    }
}
