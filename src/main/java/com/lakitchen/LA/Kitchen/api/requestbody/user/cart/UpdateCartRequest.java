package com.lakitchen.LA.Kitchen.api.requestbody.user.cart;

import lombok.Data;

@Data
public class UpdateCartRequest {
    Integer userId;
    Integer productId;
    Integer quantity;
    String note;
}
