package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductAdminDTO {
    Integer id;
    String name;
    Integer price;
    String category;
    String subCategory;
    Integer popularity;
    Double rating;
    Integer sold;
    Integer isActive;
}
