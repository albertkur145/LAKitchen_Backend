package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.role_user.product.ProductGeneralDTO;
import com.lakitchen.LA.Kitchen.model.entity.Product;
import org.springframework.stereotype.Service;

@Service
public class ProductMapper {

    public ProductGeneralDTO mapToProductGeneralDTO(Product product, String photoLink, Double rating, Integer totalAssessment) {
        return new ProductGeneralDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                photoLink,
                rating,
                totalAssessment
        );
    }

}
