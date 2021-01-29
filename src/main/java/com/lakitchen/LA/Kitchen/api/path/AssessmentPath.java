package com.lakitchen.LA.Kitchen.api.path;

import com.lakitchen.LA.Kitchen.api.path.config.ApiConfig;

public class AssessmentPath {

    private static final String API = ApiConfig.API;
    private static final String ADMIN_API = ApiConfig.ADMIN;

    // ROLE_USER ENDPOINT
    private static final String ASSESSMENT = API + "/assessment";
    public static final String ASSESSMENT_POST = ASSESSMENT + "";
    public static final String ASSESSMENT_GET_ALL = ASSESSMENT + "";

    // ROLE_ADMIN ENDPOINT
    private static final String ADMIN_ASSESSMENT = ADMIN_API + "/assessment";
    public static final String ADMIN_ASSESSMENT_GET_DETAIL = ADMIN_ASSESSMENT + "";
    public static final String ADMIN_ASSESSMENT_GET_ALL_COMMENT = ADMIN_ASSESSMENT + "/comment";
    public static final String ADMIN_ASSESSMENT_DELETE_COMMENT = ADMIN_ASSESSMENT + "/comment";

}
