package com.lakitchen.LA.Kitchen.api.response.data.role_admin.product;

import com.lakitchen.LA.Kitchen.api.dto.ProductAdminDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetByNameAdmin {
    ArrayList<ProductAdminDTO> products;
}
