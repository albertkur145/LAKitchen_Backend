package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.CartPath;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.cart.SaveCartRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.cart.UpdateCartRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class CartController {

    @Autowired
    private CartServiceImpl cartService;

    // ROLE_USER
    @PostMapping(CartPath.CART_POST)
    public ResponseTemplate saveProductToCart(@RequestBody SaveCartRequest objParam) {
        return cartService.saveProductToCart(objParam);
    }

    // ROLE_USER
    @PutMapping(CartPath.CART_PUT)
    public ResponseTemplate updateCart(@RequestBody UpdateCartRequest objParam) {
        return cartService.updateCart(objParam);
    }

    // ROLE_USER
    @DeleteMapping(CartPath.CART_DELETE)
    public ResponseTemplate deleteCart(@RequestParam("userId") Integer userId,
                                       @RequestParam("productId") Integer productId) {
        return cartService.deleteCart(userId, productId);
    }

    // ROLE_USER
    @GetMapping(CartPath.CART_GET_ALL)
    public ResponseTemplate getAll(@RequestParam("userId") Integer userId) {
        return cartService.getAll(userId);
    }

    // ROLE_USER
    @GetMapping(CartPath.CART_COUNT)
    public ResponseTemplate countUserCart(@RequestParam("userId") Integer userId) {
        return cartService.countUserCart(userId);
    }

}
