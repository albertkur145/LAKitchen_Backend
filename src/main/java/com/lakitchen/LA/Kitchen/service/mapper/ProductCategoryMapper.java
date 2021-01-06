package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.CategoryDTO;
import com.lakitchen.LA.Kitchen.api.dto.SubCategoryDTO;
import com.lakitchen.LA.Kitchen.api.dto.format.SubCategoryFormat;
import com.lakitchen.LA.Kitchen.model.entity.ProductCategory;
import com.lakitchen.LA.Kitchen.model.entity.ProductSubCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductCategoryMapper {

    public CategoryDTO mapToCategoryDTO(ProductCategory productCategory) {
        return new CategoryDTO(productCategory.getId(), productCategory.getName());
    }

    public SubCategoryDTO mapToSubCategoryDTO(ProductCategory productCategory, ArrayList subCategoryDTO) {
        return new SubCategoryDTO(
                productCategory.getId(),
                productCategory.getName(),
                subCategoryDTO);
    }

    public SubCategoryFormat productSubCategoryFormat(ProductSubCategory productSubCategory) {
        return new SubCategoryFormat(productSubCategory.getId(), productSubCategory.getName());
    }
}
