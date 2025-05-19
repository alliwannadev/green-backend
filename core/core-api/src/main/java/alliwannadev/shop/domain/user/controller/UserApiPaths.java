package alliwannadev.shop.domain.user.controller;

import alliwannadev.shop.common.constant.ApiPaths;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class UserApiPaths {

    public static final String V1_USERS = ApiPaths.V1 + "/users";
    public static final String V1_USERS_ME = V1_USERS + "/me";
}
