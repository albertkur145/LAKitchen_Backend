package com.lakitchen.LA.Kitchen.api.response.data.role_admin.order;

import com.lakitchen.LA.Kitchen.api.dto.OrderGeneralDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetUnprocessed {
    ArrayList<OrderGeneralDTO> orders;
}
