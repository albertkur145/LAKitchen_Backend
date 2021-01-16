package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.role_user.wishlist.SaveWishlistRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface WishlistService {

    ResponseTemplate saveProductWishlist(SaveWishlistRequest request);
    ResponseTemplate removeWishlist(Integer userId, Integer productId);
    ResponseTemplate getAll(Integer userId);

}
