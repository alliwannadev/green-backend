package alliwannadev.shop.api.warehousing.controller;

import alliwannadev.shop.common.IntegrationTest;
import alliwannadev.shop.common.TestContainers;
import alliwannadev.shop.api.auth.support.TestAuthDbUtil;
import alliwannadev.shop.api.option.support.TestProductOptionCombinationDbUtil;
import alliwannadev.shop.api.option.support.TestProductOptionDbUtil;
import alliwannadev.shop.core.application.modules.option.service.dto.CreateProductOptionCombinationParam;
import alliwannadev.shop.core.application.modules.option.service.dto.CreateProductOptionParam;
import alliwannadev.shop.api.product.support.TestProductDbUtil;
import alliwannadev.shop.core.application.modules.product.service.dto.CreateProductParam;
import alliwannadev.shop.api.stock.support.TestStockDbUtil;
import alliwannadev.shop.api.warehousing.controller.dto.CreateProductWarehousingRequestV1;
import alliwannadev.shop.api.user.support.TestUserDbUtil;
import alliwannadev.shop.core.jpa.option.model.ProductOptionCombination;
import alliwannadev.shop.core.jpa.product.model.Product;
import alliwannadev.shop.core.jpa.stock.model.Stock;
import alliwannadev.shop.support.dataserializer.DataSerializer;
import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpHeaders;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@DisplayName("통합 테스트 - 상품 입고 API V1")
@IntegrationTest
class ProductWarehousingApiV1Test extends TestContainers {

    @Autowired MockMvc mockMvc;
    @Autowired StringRedisTemplate redisTemplate;
    @Autowired TestAuthDbUtil testAuthDbUtil;
    @Autowired TestUserDbUtil testUserDbUtil;
    @Autowired TestProductDbUtil testProductDbUtil;
    @Autowired TestProductOptionDbUtil testProductOptionDbUtil;
    @Autowired TestProductOptionCombinationDbUtil testProductOptionCombinationDbUtil;
    @Autowired TestStockDbUtil testStockDbUtil;

    @AfterEach
    void tearDown() {
        Collection<String> redisKeys = redisTemplate.keys("*");
        redisTemplate.delete(redisKeys);

        testUserDbUtil.deleteAll();
        testProductDbUtil.deleteAll();
        testProductOptionDbUtil.deleteAll();
        testProductOptionCombinationDbUtil.deleteAll();
        testStockDbUtil.deleteAll();
    }

    @DisplayName("[API][POST][SUCCESS] 특정 상품의 수량을 1개 증가하는 입고 데이터 생성을 1번 요청하면 해당 상품의 재고는 1개여야 한다.")
    @Test
    void createOne() throws Exception {
        // Given
        testAuthDbUtil.createDefaultTestUserIfNotExists();
        String accessToken = testAuthDbUtil.getDefaultToken();
        Product product = createProduct();
        ProductOptionCombination productOptionCombination =
                testProductOptionCombinationDbUtil
                        .getByCond(
                                product.getProductId(),
                                "색상_COLOR_BLACK/사이즈_SIZE_M"
                        )
                        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_COMBINATION_NOT_FOUND));

        CreateProductWarehousingRequestV1 createProductWarehousingRequestV1 =
                new CreateProductWarehousingRequestV1(
                        productOptionCombination.getProductOptionCombinationId(),
                        "20250518",
                        1L
                );
        String requestBody = DataSerializer.serialize(createProductWarehousingRequestV1);

        // When & Then
        mockMvc.perform(
                        post(ProductWarehousingApiPaths.V1_WAREHOUSING)
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
                .andExpect(jsonPath("$.message").value("상품 입고를 완료했습니다."));

        Stock stock = testStockDbUtil
                .getOneByCombinationId(productOptionCombination.getProductOptionCombinationId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));
        assertThat(stock.getQuantity()).isEqualTo(1L);
    }

    @DisplayName("[API][POST][SUCCESS] 특정 상품의 수량을 1개 증가하는 입고 데이터 생성을 150번 요청하면 해당 상품의 재고는 150개여야 한다.")
    @Test
    void createMultiple() throws InterruptedException {
        // Given
        testAuthDbUtil.createDefaultTestUserIfNotExists();
        String accessToken = testAuthDbUtil.getDefaultToken();
        Product product = createProduct();
        ProductOptionCombination productOptionCombination =
                testProductOptionCombinationDbUtil
                        .getByCond(
                                product.getProductId(),
                                "색상_COLOR_BLACK/사이즈_SIZE_M"
                        )
                        .orElseThrow(() -> new BusinessException(ErrorCode.PRODUCT_OPTION_COMBINATION_NOT_FOUND));

        int requestCount = 150;
        CreateProductWarehousingRequestV1 createProductWarehousingRequestV1 =
                new CreateProductWarehousingRequestV1(
                        productOptionCombination.getProductOptionCombinationId(),
                        "20250518",
                        1L
                );
        String requestBody = DataSerializer.serialize(createProductWarehousingRequestV1);

        ExecutorService executorService = Executors.newFixedThreadPool(32);
        CountDownLatch countDownLatch = new CountDownLatch(requestCount);

        // When & Then
        for (int i = 1; i <= requestCount; i++) {
            executorService.submit(
                    () -> {
                        try {
                            mockMvc.perform(
                                            post(ProductWarehousingApiPaths.V1_WAREHOUSING)
                                                    .header(
                                                            HttpHeaders.AUTHORIZATION,
                                                            accessToken
                                                    )
                                                    .contentType(APPLICATION_JSON)
                                                    .content(requestBody)
                                    )
                                    .andExpect(status().isOk());
                        } catch (Exception e) {
                            log.error(e.getMessage(), e);
                        } finally {
                            countDownLatch.countDown();
                        }
                    }
            );
        }

        countDownLatch.await();
        Stock stock = testStockDbUtil
                .getOneByCombinationId(productOptionCombination.getProductOptionCombinationId())
                .orElseThrow(() -> new BusinessException(ErrorCode.STOCK_NOT_FOUND));
        assertThat(stock.getQuantity()).isEqualTo(requestCount);
    }

    private Product createProduct() {
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
}
