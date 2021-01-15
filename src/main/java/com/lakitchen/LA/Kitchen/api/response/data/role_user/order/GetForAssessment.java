package com.lakitchen.LA.Kitchen.api.response.data.role_user.order;

import com.lakitchen.LA.Kitchen.api.dto.ProductSimplifiedDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetForAssessment {
    String orderNumber;
    String date;
    ProductSimplifiedDTO product;
}
