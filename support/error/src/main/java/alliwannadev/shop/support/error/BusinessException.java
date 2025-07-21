package alliwannadev.shop.support.error;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    private final List<FieldError> errors = new ArrayList<>();

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }

    public BusinessException(ErrorCode errorCode, List<FieldError> errors) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.errors.addAll(errors);
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}
