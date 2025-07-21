package alliwannadev.shop.core.api.product.controller;

import alliwannadev.shop.core.api.common.constant.ApiPaths;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ProductApiPaths {

    public static final String V1_PRODUCTS = ApiPaths.V1 + "/products";
    public static final String V1_PRODUCTS_INFINITE_SCROLL = V1_PRODUCTS + "/infinite-scroll";
    public static final String V1_PRODUCTS_BY_PRODUCT_ID = V1_PRODUCTS + "/{productId}";
}
