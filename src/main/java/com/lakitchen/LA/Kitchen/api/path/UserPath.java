package com.lakitchen.LA.Kitchen.api.path;

import com.lakitchen.LA.Kitchen.api.path.config.ApiConfig;

public class UserPath {

    private static final String API = ApiConfig.API;
    private static final String ADMIN_API = ApiConfig.ADMIN;

    // ROLE_USER ENDPOINT
    private static final String USER = API + "/user";
    public static final String USER_POST = USER + "";
    public static final String USER_PUT = USER + "";
    public static final String USER_CHANGE_PASSWORD = USER + "/changepassword";

    // ROLE_ADMIN ENDPOINT
    private static final String ADMIN_USER = ADMIN_API + "/user";
    public static final String ADMIN_GET_ALL_STATUS = ADMIN_USER + "/status";
    public static final String ADMIN_RESET_PASSWORD = ADMIN_USER + "/reset";
    public static final String ADMIN_STATUS_UPDATE = ADMIN_USER + "/activation";

    private static final String ADMIN_EMPLOYEE = ADMIN_API + "/employee";
    public static final String ADMIN_POST_EMPLOYEE = ADMIN_EMPLOYEE + "";
    public static final String ADMIN_PUT_EMPLOYEE = ADMIN_EMPLOYEE + "";
    public static final String ADMIN_GET_EMPLOYEE_BY_ID = ADMIN_EMPLOYEE + "/id";
    public static final String ADMIN_GET_ALL_EMPLOYEE = ADMIN_EMPLOYEE + "";
    public static final String ADMIN_GET_EMPLOYEE_BY_STATUS = ADMIN_EMPLOYEE + "/status";
    public static final String ADMIN_GET_EMPLOYEE_BY_NAME = ADMIN_EMPLOYEE + "/search";

    private static final String ADMIN_CUSTOMER = ADMIN_API + "/customer";
    public static final String ADMIN_GET_ALL_CUSTOMER = ADMIN_CUSTOMER + "";
    public static final String ADMIN_GET_CUSTOMER_BY_STATUS = ADMIN_CUSTOMER + "/status";
    public static final String ADMIN_GET_CUSTOMER_BY_NAME = ADMIN_CUSTOMER + "/search";

}
