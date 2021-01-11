package com.lakitchen.LA.Kitchen.api.path;

import com.lakitchen.LA.Kitchen.api.path.config.ApiConfig;

public class ProductCategoryPath {

    private static final String API = ApiConfig.API;

    // PRODUCT CATEGORY ENDPOINTS
    private static final String CATEGORY = API + "/categories";
    public static final String CATEGORY_GET_ALL = CATEGORY + "";
    public static final String CATEGORY_GET_ALL_WITH_SUB = CATEGORY + "andsub";
    public static final String CATEGORY_RESET = CATEGORY + "/reset";

}
