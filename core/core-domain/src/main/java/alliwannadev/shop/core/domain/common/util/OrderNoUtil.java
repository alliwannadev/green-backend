package alliwannadev.shop.core.domain.common.util;

import java.util.UUID;

public class OrderNoUtil {

    public static String createOrderNo() {
        return UUID
                .randomUUID()
                .toString()
                .replace("-", "")
                .toUpperCase();
    }
}
