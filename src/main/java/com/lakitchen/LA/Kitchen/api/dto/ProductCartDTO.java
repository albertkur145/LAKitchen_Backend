package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCartDTO {
    Integer id;
    String name;
    Integer price;
    String photo_link;
    Integer quantity;
    String note;
}
