package alliwannadev.shop.common.constant;

import org.apache.commons.lang3.StringUtils;

public enum UserSearchType {
    EMAIL, PHONE, UNUSED;

    public static UserSearchType from(String searchType) {
        return StringUtils.isBlank(searchType) ?
                UserSearchType.UNUSED :
                UserSearchType.valueOf(searchType.toUpperCase());
    }
}
