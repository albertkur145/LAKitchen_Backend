package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.ReportPath;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ReportController {

    @Autowired
    ReportServiceImpl reportService;

    @GetMapping(ReportPath.ADMIN_GET_DASHBOARD)
    public ResponseTemplate getAllDashboard() {
        return reportService.getAllDashboard();
    }

    @GetMapping(ReportPath.ADMIN_GET_SALES_TODAY)
    public ResponseTemplate getSalesToday() {
        return reportService.getSalesToday();
    }

    @GetMapping(ReportPath.ADMIN_GET_MONTHLY_INCOME)
    public ResponseTemplate getMonthlyIncome(@RequestParam("year") String year,
                                             @RequestParam("month") String month) {
        return reportService.getMonthlyIncome(year, month);
    }

    @GetMapping(ReportPath.ADMIN_GET_ANNUAL_INCOME)
    public ResponseTemplate getAnnualIncome(@RequestParam("year") String year) {
        return reportService.getAnnualIncome(year);
    }

}
