package com.lakitchen.LA.Kitchen.api.response.data;

import com.lakitchen.LA.Kitchen.api.dto.CategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetCategories {
    public ArrayList<CategoryDTO> categories;
}
