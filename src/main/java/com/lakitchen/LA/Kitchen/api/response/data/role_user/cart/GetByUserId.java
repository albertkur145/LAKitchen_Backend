package com.lakitchen.LA.Kitchen.api.response.data.role_user.cart;

import com.lakitchen.LA.Kitchen.api.dto.ProductCartDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetByUserId {
    ArrayList<ProductCartDTO> products;
}
