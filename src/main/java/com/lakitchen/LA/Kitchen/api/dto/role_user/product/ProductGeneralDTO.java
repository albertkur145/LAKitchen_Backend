package com.lakitchen.LA.Kitchen.api.dto.role_user.product;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductGeneralDTO {
    Integer id;
    String name;
    Integer price;
    String photo_link;
    Double rating;
    Integer evaluators;
}
