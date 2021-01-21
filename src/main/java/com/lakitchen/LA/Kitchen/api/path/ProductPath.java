package com.lakitchen.LA.Kitchen.api.path;

import com.lakitchen.LA.Kitchen.api.path.config.ApiConfig;

public class ProductPath {

    private static final String API = ApiConfig.API;
    private static final String ADMIN_API = ApiConfig.ADMIN;

    // ROLE_USER ENDPOINT
    private static final String PRODUCT = API + "/product";
    public static final String GET_PRODUCT_BY_SUBCATEGORY = PRODUCT + "/subcategory";
    public static final String GET_PRODUCT_BY_CATEGORY = PRODUCT + "/category";
    public static final String GET_PRODUCT_BY_NAME = PRODUCT + "/name";
    public static final String GET_PRODUCT_BY_PRICE = PRODUCT + "/price";
    public static final String GET_PRODUCT_BY_ID = PRODUCT + "/id";
    public static final String INCREMENT_SEEN = PRODUCT + "/seen";

    // ROLE_ADMIN ENDPOINT
    private static final String ADMIN_PRODUCT = ADMIN_API + "/product";
    public static final String ADMIN_PRODUCT_GET_ALL = ADMIN_PRODUCT + "";
    public static final String ADMIN_PRODUCT_POST = ADMIN_PRODUCT + "";
    public static final String ADMIN_PRODUCT_PUT = ADMIN_PRODUCT + "";
    public static final String ADMIN_PRODUCT_UPLOAD_PHOTO = ADMIN_PRODUCT + "/upload";
    public static final String ADMIN_PRODUCT_GET_TOP_FAVORITE = ADMIN_PRODUCT + "/favorite";
    public static final String ADMIN_PRODUCT_GET_TOP_SELLING = ADMIN_PRODUCT + "/bestselling";
    public static final String ADMIN_PRODUCT_GET_TOP_RATING = ADMIN_PRODUCT + "/bestrating";
    public static final String ADMIN_PRODUCT_GET_TOP_FAVORITE_BY_CATEGORY = ADMIN_PRODUCT + "/favorite/category";
    public static final String ADMIN_PRODUCT_GET_TOP_SELLING_BY_CATEGORY = ADMIN_PRODUCT + "/bestselling/category";
}
