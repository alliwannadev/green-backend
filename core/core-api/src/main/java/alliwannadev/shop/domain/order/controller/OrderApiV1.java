package alliwannadev.shop.domain.order.controller;

import alliwannadev.shop.common.dto.OkResponse;
import alliwannadev.shop.common.security.CustomUser;
import alliwannadev.shop.domain.order.controller.dto.CreateOrderRequestV1;
import alliwannadev.shop.domain.order.controller.dto.CreateOrderResponseV1;
import alliwannadev.shop.domain.order.service.OrderService;
import alliwannadev.shop.domain.order.service.dto.CreateOrderResult;
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

    private final OrderService orderService;

    @PostMapping(OrderApiPaths.V1_ORDERS)
    public OkResponse<CreateOrderResponseV1> createOrder(
            @Valid @RequestBody CreateOrderRequestV1 createOrderRequestV1,
            @AuthenticationPrincipal CustomUser customUser
    ) {
        CreateOrderResult createOrderResult = orderService.create(
                createOrderRequestV1.toDto(customUser.getUserId())
        );
        return OkResponse.of(CreateOrderResponseV1.fromDto(createOrderResult));
    }
}
