package alliwannadev.shop.core.domain.common.util;

import alliwannadev.shop.core.domain.common.constant.OrderStatus;
import alliwannadev.shop.core.domain.common.error.BusinessException;
import alliwannadev.shop.core.domain.common.error.ErrorCode;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@Converter
public class OrderStatusConverter implements AttributeConverter<OrderStatus, String> {

    @Override
    public String convertToDatabaseColumn(OrderStatus orderStatus) {
        if (ObjectUtils.isEmpty(orderStatus)) {
            throw new BusinessException(
                    ErrorCode.INVALID_INPUT_VALUE,
                    "OrderStatus가 null 입니다."
            );
        }

        return orderStatus.getCode();
    }

    @Override
    public OrderStatus convertToEntityAttribute(String code) {
        if (StringUtils.isBlank(code)) {
            throw new BusinessException(
                    ErrorCode.INVALID_INPUT_VALUE,
                    "OrderStatus의 code가 올바르지 않습니다."
            );
        }

        return OrderStatus.getByCode(code);
    }
}
