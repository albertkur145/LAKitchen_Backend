package com.lakitchen.LA.Kitchen.api.response.data.role_user.order;

import com.lakitchen.LA.Kitchen.api.dto.OrderGeneralDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductOrder2DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetAllForAssessment {
    OrderGeneralDTO order;
    ArrayList<ProductOrder2DTO> products;
}
