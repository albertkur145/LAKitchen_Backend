package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductAssessmentDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductPhotoDTO;
import com.lakitchen.LA.Kitchen.api.dto.role_user.product.ProductDetailDTO;
import com.lakitchen.LA.Kitchen.api.dto.role_user.product.ProductGeneralDTO;
import com.lakitchen.LA.Kitchen.model.entity.Product;
import com.lakitchen.LA.Kitchen.model.entity.ProductAssessment;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@Service
public class ProductMapper {

    @Autowired
    UserRepository userRepository;

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

    public ProductDetailDTO mapToProductDetailDTO(Product product, ArrayList<ProductPhotoDTO> productPhotoDTOS,
                                                  ArrayList<ProductAssessment> productAssessments,
                                                  Double rating, Integer totalAssessment) {
        ArrayList<ProductAssessmentDTO> assessmentDTOS = new ArrayList<>();
        productAssessments.forEach((val) -> {
            if (val.getDeletedAt() == null) {
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                String dateOfAssessment = dateFormat.format(val.getCreatedAt());
                IdNameDTO userDTO = new IdNameDTO(val.getUser().getId(), val.getUser().getName());
                ProductAssessmentDTO dto = new ProductAssessmentDTO(val.getId(), userDTO,
                        val.getRate().intValue(), val.getComment(), dateOfAssessment);
                assessmentDTOS.add(dto);
            }
        });

        return new ProductDetailDTO(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getSeen(),
                rating,
                totalAssessment,
                productPhotoDTOS,
                assessmentDTOS
        );
    }

}
