package com.lakitchen.LA.Kitchen.api.path;

import com.lakitchen.LA.Kitchen.api.path.config.ApiConfig;

public class ReportPath {

    private static final String ADMIN_API = ApiConfig.ADMIN;

    // ROLE_ADMIN
    public static final String ADMIN_GET_DASHBOARD = ADMIN_API + "/dashboard";
    public static final String ADMIN_REPORT = ADMIN_API + "/report";
    public static final String ADMIN_GET_SALES_TODAY = ADMIN_REPORT + "/salestoday";
    public static final String ADMIN_GET_MONTHLY_INCOME = ADMIN_REPORT + "/income/monthly";
    public static final String ADMIN_GET_ANNUAL_INCOME = ADMIN_REPORT + "/income/annual";

}
