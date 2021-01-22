package com.lakitchen.LA.Kitchen.api.dto;

import com.lakitchen.LA.Kitchen.model.entity.ProductPhoto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class ProductDetail2DTO {
    Integer id;
    String name;
    Integer price;
    String description;
    Integer categoryId;
    Integer subCategoryId;
    Integer isActive;
    ArrayList<ProductPhotoDTO> photo_links;
}
