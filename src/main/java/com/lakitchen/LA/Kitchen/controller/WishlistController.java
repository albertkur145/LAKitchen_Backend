package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.WishlistPath;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.wishlist.SaveWishlistRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.WishlistServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class WishlistController {

    @Autowired
    WishlistServiceImpl wishlistService;

    // ROLE_USER
    @PostMapping(WishlistPath.WISHLIST_POST)
    public ResponseTemplate saveProductWishlist(@RequestBody SaveWishlistRequest objParam) {
        return wishlistService.saveProductWishlist(objParam);
    }

    // ROLE_USER
    @DeleteMapping(WishlistPath.WISHLIST_DELETE)
    public ResponseTemplate removeWishlist(@RequestParam("userId") Integer userId,
                                           @RequestParam("productId") Integer productId) {
        return wishlistService.removeWishlist(userId, productId);
    }

    // ROLE_USER
    @GetMapping(WishlistPath.WISHLIST_GET_ALL)
    public ResponseTemplate getAll(@RequestParam("userId") Integer userId) {
        return wishlistService.getAll(userId);
    }

}
