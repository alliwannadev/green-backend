package alliwannadev.shop.core.application.common.util;

import alliwannadev.shop.support.error.BusinessException;
import alliwannadev.shop.support.error.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class CustomValidator {

    public static void isPositiveNumber(Long number) {
        if (number == null || number <= 0) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    public static void isPositiveNumber(Long... numbers) {
        for (Long number : numbers) {
            isPositiveNumber(number);
        }
    }

    public static void isPositiveNumberOrZero(Long number) {
        if (number == null || number < 0) {
            throw new BusinessException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    public static void isPositiveNumberOrZero(Long... numbers) {
        for (Long number : numbers) {
            isPositiveNumberOrZero(number);
        }
    }
}
