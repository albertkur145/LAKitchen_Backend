package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.role_user.cart.SaveCartRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.cart.UpdateCartRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface CartService {

    ResponseTemplate saveProductToCart(SaveCartRequest request);
    ResponseTemplate updateCart(UpdateCartRequest request);
    ResponseTemplate deleteCart(Integer userId, Integer productId);
    ResponseTemplate getAll(Integer userId);
    ResponseTemplate countUserCart(Integer userId);

}
