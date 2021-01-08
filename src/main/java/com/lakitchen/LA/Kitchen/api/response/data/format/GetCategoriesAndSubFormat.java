package com.lakitchen.LA.Kitchen.api.response.data.format;

import com.lakitchen.LA.Kitchen.api.dto.CategoryDTO;
import com.lakitchen.LA.Kitchen.api.dto.SubCategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetCategoriesAndSubFormat {
    Integer id;
    String name;
    ArrayList<SubCategoryDTO> subCategories;
}
