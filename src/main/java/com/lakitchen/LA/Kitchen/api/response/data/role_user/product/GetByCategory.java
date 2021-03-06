package com.lakitchen.LA.Kitchen.api.response.data.role_user.product;

import com.lakitchen.LA.Kitchen.api.dto.ProductGeneralDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetByCategory {
    String title;
    ArrayList<ProductGeneralDTO> products;
}
