package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.CategoryDTO;
import com.lakitchen.LA.Kitchen.api.response.data.format.GetCategoriesAndSubFormat;
import com.lakitchen.LA.Kitchen.api.dto.SubCategoryDTO;
import com.lakitchen.LA.Kitchen.model.entity.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductCategoryMapper {

    public CategoryDTO mapToCategoryDTO(ProductCategory productCategory) {
        return new CategoryDTO(productCategory.getId(), productCategory.getName());
    }

    public GetCategoriesAndSubFormat mapToCategoryAndSub
            (CategoryDTO categoryDTO, ArrayList<SubCategoryDTO> subCategoryDTOS) {
        return new GetCategoriesAndSubFormat(categoryDTO.getId(), categoryDTO.getName(), subCategoryDTOS);
    }
}
