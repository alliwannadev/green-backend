package alliwannadev.shop.common.util;

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
