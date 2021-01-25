package com.lakitchen.LA.Kitchen.api.response.data.role_admin.order;

import com.lakitchen.LA.Kitchen.api.dto.OrderAdminDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductOrderDTO;
import com.lakitchen.LA.Kitchen.api.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetById {
    UserDTO customer;
    OrderAdminDTO order;
    ArrayList<ProductOrderDTO> products;
}
