package alliwannadev.shop.common.constant;

import alliwannadev.shop.common.error.BusinessException;
import alliwannadev.shop.common.error.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.text.MessageFormat;
import java.util.Arrays;

@Getter
@RequiredArgsConstructor
public enum OrderStatus {

    // 기본적인 주문 프로세스
    ORDER_REQUEST("ORDER_REQ", "주문 요청"),
    ORDER_COMPLETED("ORDER_CPD", "주문 완료"),
    RELEASE_REQUEST("RELSE_REQ", "출고 요청"),
    RELEASE_IN_PROGRESS("RELSE_IN", "출고 처리 중"),
    RELEASE_COMPLETED("RELSE_CPD", "출고 완료"),
    DELIVERY_START("DLY_START", "배송 시작"),
    DELIVERY_COMPLETED("DLY_CPD", "배송 완료"),
    PURCHASE_CONFIRMED("PCE_CFD", "구매 확정"),
    ORDER_CANCELLED("ORDER_CAD", "주문 취소"),

    // 교환
    EXCHANGE_REQUEST("EXG_REQ", "교환 요청"),
    EXCHANGE_COMPLETED("EXG_CPD", "교환 완료"),
    EXCHANGE_CANCELLED("EXG_CLD", "교환 취소"),

    // 환불
    REFUND_REQUEST("RFD_REQ", "환불 요청"),
    REFUND_IN_PROGRESS("RFD_IN", "환불 처리 중"),
    REFUND_COMPLETED("RFD_CPD", "환불 완료"),
    REFUND_CANCELLED("RFD_CLD", "환불 취소");

    private final String code;
    private final String description;

    public static OrderStatus getByCode(String code) {
        return Arrays.stream(OrderStatus.values())
                .filter(status -> code.equalsIgnoreCase(status.code))
                .findFirst()
                .orElseThrow(() -> new BusinessException(
                        ErrorCode.INVALID_INPUT_VALUE,
                        MessageFormat.format("존재하지 않는 코드({0})를 전달했습니다.", code)
                ));
    }
}
