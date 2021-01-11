package com.lakitchen.LA.Kitchen.api.path;

import com.lakitchen.LA.Kitchen.api.path.config.ApiConfig;

public class WishlistPath {

    private static final String API = ApiConfig.API;

    // ROLE_USER
    private static final String WISHLIST = API + "/wishlist";
    public static final String WISHLIST_POST = WISHLIST + "";
    public static final String WISHLIST_DELETE = WISHLIST + "";
    public static final String WISHLIST_GET_ALL = WISHLIST + "";

}
