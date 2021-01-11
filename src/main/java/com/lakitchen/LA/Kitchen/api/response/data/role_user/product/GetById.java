package com.lakitchen.LA.Kitchen.api.response.data.role_user.product;

import com.lakitchen.LA.Kitchen.api.dto.role_user.product.ProductDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetById {
    ProductDetailDTO product;
}
