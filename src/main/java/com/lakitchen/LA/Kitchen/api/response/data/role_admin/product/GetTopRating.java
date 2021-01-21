package com.lakitchen.LA.Kitchen.api.response.data.role_admin.product;

import com.lakitchen.LA.Kitchen.api.dto.ProductTopRatingDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetTopRating {
    ArrayList<ProductTopRatingDTO> products;
}
