package alliwannadev.shop.common.error;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {

    // Common
    INVALID_INPUT_VALUE(400, "COMMON-001", "잘못된 값을 입력하셨습니다."),
    METHOD_NOT_ALLOWED(405, "COMMON-002", "허용하지 않는 HTTP Method 입니다."),
    RESOURCE_NOT_FOUND(404, "COMMON-003", "요청하신 리소스(엔드포인트)를 찾을 수 없습니다."),
    DATA_ACCESS_ERROR(500, "COMMON-004", "요청하신 데이터에 접근하는 과정에서 에러가 발생했습니다."),
    INTERNAL_SERVER_ERROR(500, "COMMON-005", "서버에 에러가 발생했습니다."),
    END_POINT_NOT_FOUND(500, "COMMON-006", "요청하신 엔드포인트를 찾을 수 없습니다."),
    DATE_TIME_PARSING_ERROR(400, "COMMON-007", "날짜 및 시간을 파싱하는 과정에서 에러가 발생했습니다."),

    // User
    EMAIL_DUPLICATION(400, "USER-001", "중복된 이메일입니다."),
    USER_NOT_FOUND(404, "USER-002", "요청하신 회원 정보를 찾을 수 없습니다."),
    ALREADY_SIGNED_UP_USER(400, "USER-003", "이미 등록된 회원입니다."),

    // Auth
    UNAUTHORIZED(401, "AUTH-001", "유효한 자격 증명을 제공하지 않았습니다."),
    FORBIDDEN(403, "AUTH-002", "요청하신 리소스에 대한 접근 권한이 없습니다."),
    INVALID_ID_OR_PASSWORD(404, "AUTH-005", "아이디 또는 비밀번호가 일치하지 않습니다."),

    // Product
    PRODUCT_NOT_FOUND(404, "PRODUCT-001", "요청하신 상품 정보를 찾을 수 없습니다."),
    PRODUCT_OPTION_COMBINATION_NOT_FOUND(404, "PRODUCT-002", "요청하신 상품 옵션 조합 정보를 찾을 수 없습니다."),

    // CART
    CART_NOT_FOUND(404, "CART-001", "요청하신 장바구니 정보를 찾을 수 없습니다."),
    CART_ITEM_NOT_FOUND(404, "CART-002", "요청하신 장바구니 항목 정보를 찾을 수 없습니다."),

    // Stock
    STOCK_NOT_FOUND(404, "STOCK-001", "요청하신 재고 정보를 찾을 수 없습니다."),
    OUT_OF_STOCK(400, "STOCK-002", "해당 상품의 재고가 부족합니다.");

    private final int status;

    private final String code;

    private final String message;
}
