package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.response.data.format.GetCategoriesAndSubFormat;
import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.model.entity.ProductCategory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductCategoryMapper {

    public IdNameDTO mapToCategoryDTO(ProductCategory productCategory) {
        return new IdNameDTO(productCategory.getId(), productCategory.getName());
    }

    public GetCategoriesAndSubFormat mapToCategoryAndSub
            (IdNameDTO categoryDTO, ArrayList<IdNameDTO> subCategoryDTOS) {
        return new GetCategoriesAndSubFormat(categoryDTO.getId(), categoryDTO.getName(), subCategoryDTOS);
    }
}
