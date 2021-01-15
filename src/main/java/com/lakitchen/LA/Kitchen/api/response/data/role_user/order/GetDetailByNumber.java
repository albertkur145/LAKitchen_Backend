package com.lakitchen.LA.Kitchen.api.response.data.role_user.order;

import com.lakitchen.LA.Kitchen.api.dto.OrderDetailDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetDetailByNumber {
    OrderDetailDTO order;
}
