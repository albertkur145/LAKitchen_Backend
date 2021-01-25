package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DashboardOrderDTO {
    Integer today;
    Integer prepared;
    Integer readyToShip;
    Integer inDelivery;
}
