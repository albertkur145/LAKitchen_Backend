package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface ReportService {

    // ROLE_ADMIN
    ResponseTemplate getAllDashboard();
    ResponseTemplate getSalesToday();
    ResponseTemplate getMonthlyIncome(String year, String month);
    ResponseTemplate getAnnualIncome(String year);

}
