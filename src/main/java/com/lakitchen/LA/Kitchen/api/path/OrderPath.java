package com.lakitchen.LA.Kitchen.api.path;

import com.lakitchen.LA.Kitchen.api.path.config.ApiConfig;

public class OrderPath {

    private static final String API = ApiConfig.API;
    private static final String ADMIN_API = ApiConfig.ADMIN;

    // ROLE_USER
    private static final String ORDER = API + "/order";
    public static final String ORDER_POST = ORDER + "";
    public static final String ORDER_DELETE = ORDER + "";
    public static final String ORDER_GET_ALL = ORDER + "";
    public static final String ORDER_GET_BY_ORDER_NUMBER = ORDER + "/id";
    public static final String ORDER_GET_FOR_ASSESSMENT = ORDER + "/productdetail";
    public static final String ORDER_GET_PRODUCTS_FOR_ASSESSMENT = API + "/product/order";

    // ROLE_ADMIN
    private static final String ADMIN_ORDER = ADMIN_API + "/order";
}
