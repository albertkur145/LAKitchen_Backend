package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductOrder2DTO {
    Integer id;
    String name;
    Integer price;
    Integer quantity;
    String photo_link;
    Integer is_assessment;
}
