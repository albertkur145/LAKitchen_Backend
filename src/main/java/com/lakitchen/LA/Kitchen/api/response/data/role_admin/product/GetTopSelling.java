package com.lakitchen.LA.Kitchen.api.response.data.role_admin.product;

import com.lakitchen.LA.Kitchen.api.dto.ProductTopSellingDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetTopSelling {
    ArrayList<ProductTopSellingDTO> products;
}
