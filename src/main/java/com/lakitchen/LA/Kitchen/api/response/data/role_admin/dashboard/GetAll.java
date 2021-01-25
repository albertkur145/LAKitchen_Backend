package com.lakitchen.LA.Kitchen.api.response.data.role_admin.dashboard;

import com.lakitchen.LA.Kitchen.api.dto.DashboardOrderDTO;
import com.lakitchen.LA.Kitchen.api.dto.DashboardOthersDTO;
import com.lakitchen.LA.Kitchen.api.dto.DashboardSalesDTO;
import com.lakitchen.LA.Kitchen.api.dto.ReportDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetAll {
    DashboardOrderDTO orders;
    DashboardSalesDTO sales;
    DashboardOthersDTO others;
    ArrayList<ReportDTO> weeklyReport;
}
