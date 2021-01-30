package com.lakitchen.LA.Kitchen.api.response.data.role_admin.report;

import com.lakitchen.LA.Kitchen.api.dto.ReportDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetMonthlyIncome {
    ArrayList<ReportDTO> report;
    Integer total;
}
