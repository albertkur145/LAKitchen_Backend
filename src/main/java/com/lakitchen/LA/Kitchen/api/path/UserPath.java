package com.lakitchen.LA.Kitchen.api.path;

import com.lakitchen.LA.Kitchen.api.path.config.ApiConfig;

public class UserPath {

    private static final String API = ApiConfig.API;

    // ROLE_USER ENDPOINT
    private static final String USER = API + "/user";
    public static final String USER_POST = USER + "";
    public static final String USER_PUT = USER + "";
    public static final String USER_CHANGE_PASSWORD = USER + "/changepassword";

}
