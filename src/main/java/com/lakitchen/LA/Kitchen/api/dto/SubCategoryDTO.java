package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class SubCategoryDTO {
    Integer id;
    String name;
    ArrayList subCategories;
}
