package alliwannadev.shop.common.constant;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ProductOptionCode {

    SIZE("SIZE", "사이즈"),
    COLOR("COLOR", "색상"),
    CUSTOM("CUSTOM", "");

    private final String code;
    private final String name;
}
