package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.user.wishlist.SaveRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface WishlistService {

    ResponseTemplate saveProductWishlist(SaveRequest request);
    ResponseTemplate removeWishlist(Integer userId, Integer productId);
    ResponseTemplate getAll(Integer userId);

}
