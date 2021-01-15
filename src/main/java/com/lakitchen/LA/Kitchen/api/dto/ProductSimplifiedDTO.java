package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductSimplifiedDTO {
    Integer id;
    String name;
    String photo_link;
}
