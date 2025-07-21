package alliwannadev.shop.core.api.auth.controller;

import alliwannadev.shop.core.api.common.constant.ApiPaths;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class AuthApiPaths {

    private static final String V1_AUTH = ApiPaths.V1 + "/auth";

    public static final String V1_SIGN_IN = V1_AUTH + "/sign-in";
    public static final String V1_SIGN_UP = V1_AUTH + "/sign-up";
}
