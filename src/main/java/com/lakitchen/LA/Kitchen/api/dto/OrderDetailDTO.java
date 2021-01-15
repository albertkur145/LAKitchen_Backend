package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class OrderDetailDTO {
    String orderNumber;
    String date;
    Integer totalPayment;
    IdNameDTO status;
    ArrayList<ProductOrderDTO> products;
}
