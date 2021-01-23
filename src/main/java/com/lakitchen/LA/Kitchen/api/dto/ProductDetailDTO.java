package com.lakitchen.LA.Kitchen.api.dto;

import com.lakitchen.LA.Kitchen.api.dto.ProductAssessmentDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductPhotoDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class ProductDetailDTO {
    Integer id;
    String name;
    Integer price;
    String description;
    Integer seen;
    Double rating;
    Integer evaluators;
    Integer isActive;
    ArrayList<ProductPhotoDTO> photo_links;
    ArrayList<ProductAssessmentDTO> assessment;
}
