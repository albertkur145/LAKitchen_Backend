package com.lakitchen.LA.Kitchen.api.response.data.role_user.product;

import com.lakitchen.LA.Kitchen.api.dto.role_user.product.BySubCategoryDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetBySubCategory {
    String title;
    ArrayList<BySubCategoryDTO> products;
}
