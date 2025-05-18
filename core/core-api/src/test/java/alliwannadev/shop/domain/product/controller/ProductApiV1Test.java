package alliwannadev.shop.domain.product.controller;

import alliwannadev.shop.common.IntegrationTest;
import alliwannadev.shop.common.TestContainers;
import alliwannadev.shop.common.error.ErrorCode;
import alliwannadev.shop.domain.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.domain.option.service.dto.CreateProductOptionParam;
import alliwannadev.shop.domain.product.support.TestProductDbUtil;
import alliwannadev.shop.domain.product.model.Product;
import alliwannadev.shop.domain.product.service.dto.CreateProductParam;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@DisplayName("통합 테스트 - 상품 API V1")
@Transactional
@IntegrationTest
class ProductApiV1Test extends TestContainers {

    @Autowired MockMvc mockMvc;
    @Autowired TestProductDbUtil testProductDbUtil;

    @DisplayName("[API][GET][SUCCESS] 상품 목록 조회 API 호출")
    @Test
    void givenProductSearchParameters_whenSearchProductList_thenReturnSuccessfulResult() throws Exception {
        // Given
        Long size = 10L;
        Long page = 1L;
        Long categoryId = 1L;
        createProduct();

        // When & Then
        mockMvc.perform(
                        get(ProductApiPaths.V1_PRODUCTS)
                                .queryParam("categoryId", String.valueOf(categoryId))
                                .queryParam("size", String.valueOf(size))
                                .queryParam("page", String.valueOf(page))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.data.content").isArray())
                .andExpect(jsonPath("$.data.content").isNotEmpty())
                .andExpect(jsonPath("$.message").value("API 요청을 완료하였습니다."));
    }

    @DisplayName("[API][GET][FAIL] 상품 목록 조회 API 호출 - 잘못된 파라미터를 전달하는 경우")
    @Test
    void givenInvalidProductSearchParameters_whenSearchProductList_thenReturnFailedResponse() throws Exception {
        // Given
        Long categoryId = -1L;

        // When & Then
        mockMvc.perform(
                        get(ProductApiPaths.V1_PRODUCTS)
                                .queryParam("categoryId", String.valueOf(categoryId))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

    @DisplayName("[API][GET][SUCCESS] 상품 1건 조회 API 호출")
    @Test
    void givenProductId_whenSearchProduct_thenReturnSuccessfulResult() throws Exception {
        // Given
        createProduct();
        Product product = testProductDbUtil.getFirstProductId();
        Long productId = product.getProductId();

        // When & Then
        mockMvc.perform(
                        get(ProductApiPaths.V1_PRODUCTS_BY_PRODUCT_ID
                                .replace("{productId}", String.valueOf(productId)))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.data.productId").value(productId))
                .andExpect(jsonPath("$.data.productCode").value(product.getProductCode()))
                .andExpect(jsonPath("$.data.productName").value(product.getProductName()))
                .andExpect(jsonPath("$.data.modelName").value(product.getModelName()))
                .andExpect(jsonPath("$.data.originalPrice").value(product.getOriginalPrice()))
                .andExpect(jsonPath("$.data.sellingPrice").value(product.getSellingPrice()))
                .andExpect(jsonPath("$.data.description").value(product.getDescription()))
                .andExpect(jsonPath("$.data.isDisplayed").value(product.getIsDisplayed()))
                .andExpect(jsonPath("$.message").value("API 요청을 완료하였습니다."));
    }

    @DisplayName("[API][GET][FAIL] 상품 1건 조회 API 호출 - 잘못된 상품 ID를 전달하는 경우")
    @Test
    void givenInvalidProductId_whenSearchProduct_thenReturnFailedResponse() throws Exception {
        // Given
        Long productId = -1L;

        // When & Then
        mockMvc.perform(
                        get(ProductApiPaths.V1_PRODUCTS_BY_PRODUCT_ID
                                .replace("{productId}", String.valueOf(productId)))
                                .contentType(APPLICATION_JSON)
                )
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().contentTypeCompatibleWith(APPLICATION_JSON))
                .andExpect(jsonPath("$.code").value(ErrorCode.INVALID_INPUT_VALUE.getCode()))
                .andExpect(jsonPath("$.message").value(ErrorCode.INVALID_INPUT_VALUE.getMessage()));
    }

    private void createProduct() {
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
        testProductDbUtil.createProduct(createProductParam);
    }
}
