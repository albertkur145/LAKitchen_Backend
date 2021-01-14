package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderGeneralDTO {
    String orderNumber;
    String date;
    Integer totalPayment;
    IdNameDTO status;
}
