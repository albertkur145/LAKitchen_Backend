package com.lakitchen.LA.Kitchen.api.requestbody.user.cart;

import lombok.Data;

@Data
public class SaveCartRequest {
    Integer userId;
    Integer productId;
    Integer quantity;
    String note;
}
