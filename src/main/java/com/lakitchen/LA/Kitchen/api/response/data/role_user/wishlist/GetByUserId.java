package com.lakitchen.LA.Kitchen.api.response.data.role_user.wishlist;

import com.lakitchen.LA.Kitchen.api.dto.ProductGeneralDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetByUserId {
    ArrayList<ProductGeneralDTO> products;
}
