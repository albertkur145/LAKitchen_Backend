package com.lakitchen.LA.Kitchen.api.response.data.role_user.order;

import com.lakitchen.LA.Kitchen.api.dto.OrderGeneralDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetByUserId {
    ArrayList<OrderGeneralDTO> orders;
}
