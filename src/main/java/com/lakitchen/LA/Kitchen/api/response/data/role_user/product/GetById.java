package com.lakitchen.LA.Kitchen.api.response.data.role_user.product;

import com.lakitchen.LA.Kitchen.api.dto.PathDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetById {
    PathDTO path;
    ProductDetailDTO product;
}
