package com.lakitchen.LA.Kitchen.api.path;

public class ProductPath {

    private static final String API = "/api";

    // ROLE_USER ENDPOINT
    private static final String PRODUCT = API + "/product";
    public static final String GET_PRODUCT_BY_SUBCATEGORY = PRODUCT + "/subcategory";
    public static final String GET_PRODUCT_BY_CATEGORY = PRODUCT + "/category";
    public static final String GET_PRODUCT_BY_NAME = PRODUCT + "/name";
    public static final String GET_PRODUCT_BY_PRICE = PRODUCT + "/price";

    // ROLE_ADMIN ENDPOINT
    private static final String ADMIN_API = API + "/admin/product";
    public static final String ADMIN_PRODUCT_POST = ADMIN_API + "";
    public static final String ADMIN_PRODUCT_PUT = ADMIN_API + "";
    public static final String ADMIN_PRODUCT_UPLOAD_PHOTO = ADMIN_API + "/upload";

}
