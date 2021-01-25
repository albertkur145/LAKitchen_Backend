package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardSalesDTO {
    Integer todayIncome;
    Integer weekIncome;
    Integer soldToday;
}
