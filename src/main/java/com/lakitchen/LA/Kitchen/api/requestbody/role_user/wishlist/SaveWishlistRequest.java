package com.lakitchen.LA.Kitchen.api.requestbody.role_user.wishlist;

import lombok.Data;

@Data
public class SaveWishlistRequest {
    Integer userId;
    Integer productId;
}
