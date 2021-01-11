package com.lakitchen.LA.Kitchen.api.requestbody.user.wishlist;

import lombok.Data;

@Data
public class SaveRequest {
    Integer userId;
    Integer productId;
}
