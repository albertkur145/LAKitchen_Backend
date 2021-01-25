package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderAdminDTO {
    String orderNumber;
    String date;
    String status;
    String paymentMethod;
    Integer totalPayment;
}
