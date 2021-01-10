package com.lakitchen.LA.Kitchen.api.response.data.role_user.product;

import com.lakitchen.LA.Kitchen.api.dto.role_user.product.ProductGeneralDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetByName {
    ArrayList<ProductGeneralDTO> products;
}
