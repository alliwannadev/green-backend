package alliwannadev.shop.api.order.controller;

import alliwannadev.shop.common.dto.OkResponse;
import alliwannadev.shop.core.domain.modules.auth.CustomUser;
import alliwannadev.shop.api.order.controller.dto.CreateOrderRequestV1;
import alliwannadev.shop.api.order.controller.dto.CreateOrderResponseV1;
import alliwannadev.shop.core.domain.modules.order.service.OrderManagementService;
import alliwannadev.shop.core.domain.modules.order.service.dto.CreateOrderResult;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class OrderApiV1 {

    private final OrderManagementService orderManagementService;

    @PostMapping(OrderApiPaths.V1_ORDERS)
    public OkResponse<CreateOrderResponseV1> createOrder(
            @Valid @RequestBody CreateOrderRequestV1 createOrderRequestV1,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        CreateOrderResult createOrderResult = orderManagementService.createOrder(
                createOrderRequestV1.toDto(customUser.getUserId())
        );
        return OkResponse.of(CreateOrderResponseV1.fromDto(createOrderResult));
    }
}
