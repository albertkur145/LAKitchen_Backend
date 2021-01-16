package com.lakitchen.LA.Kitchen.api.response.data.shared.format;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetCategoriesAndSubFormat {
    Integer id;
    String name;
    ArrayList<IdNameDTO> subCategories;
}
