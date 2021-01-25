package com.lakitchen.LA.Kitchen.api.path;

import com.lakitchen.LA.Kitchen.api.path.config.ApiConfig;

public class OrderStatusPath {

    private static final String ADMIN_API = ApiConfig.ADMIN;

    // ROLE_ADMIN
    private static final String ADMIN_ORDER_STATUS = ADMIN_API + "/orderstatus";
    public static final String GET_ACTIVE_STATUS = ADMIN_ORDER_STATUS + "/active";
    public static final String GET_FINISHED_STATUS = ADMIN_ORDER_STATUS + "/history";
}
