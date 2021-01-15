package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductOrderDTO {
    Integer id;
    String name;
    Integer price;
    Integer quantity;
    String note;
    Integer subTotal;
}
