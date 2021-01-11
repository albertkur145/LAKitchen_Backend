package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.ProductGeneralDTO;
import com.lakitchen.LA.Kitchen.api.requestbody.user.wishlist.SaveRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.wishlist.GetByUserId;
import com.lakitchen.LA.Kitchen.model.entity.*;
import com.lakitchen.LA.Kitchen.repository.*;
import com.lakitchen.LA.Kitchen.service.impl.WishlistService;
import com.lakitchen.LA.Kitchen.service.mapper.ProductMapper;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class WishlistServiceImpl implements WishlistService {

    @Autowired
    WishlistRepository wishlistRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductMapper productMapper;

    @Override
    public ResponseTemplate saveProductWishlist(SaveRequest request) {
        BasicResult result = this.validationSaveProductWishlist(request);

        if (result.getResult()) {
            Wishlist wishlist = new Wishlist();
            Product product = productRepository.findFirstById(request.getProductId());
            User user = userRepository.findFirstById(request.getUserId());

            wishlist.setUser(user);
            wishlist.setProduct(product);
            wishlist.setCreatedAt(new Timestamp(System.currentTimeMillis()));
            wishlistRepository.save(wishlist);

            return new ResponseTemplate(200, "OK",null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(),null, null, result.getError());
    }

    @Override
    public ResponseTemplate removeWishlist(Integer userId, Integer productId) {
        BasicResult result = this.validationRemoveWishlist(userId, productId);

        if (result.getResult()) {
            wishlistRepository.deleteByUser_IdAndProduct_Id(userId, productId);
            return new ResponseTemplate(200, "OK", null, null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(),null, null, result.getError());
    }

    @Override
    public ResponseTemplate getAll(Integer userId) {
        BasicResult result = this.validationGetAll(userId);

        if (result.getResult()) {
            ArrayList<Wishlist> wishlists = wishlistRepository.findByUser_Id(userId);
            ArrayList<ProductGeneralDTO> dto = new ArrayList<>();

            wishlists.forEach((val) -> {
                dto.add(productMapper.setToFormatGeneralDTO(val.getProduct()));
            });

            return new ResponseTemplate(200, "OK",
                    new GetByUserId(dto), null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(),null, null, result.getError());
    }

    private BasicResult validationSaveProductWishlist(SaveRequest request) {
        if (!this.isExistUser(request.getUserId())) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }

        if (!this.isExistProduct(request.getProductId())) {
            return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
        }

        if (this.isExistWishlist(request.getUserId(), request.getProductId())) {
            return new BasicResult(false, "Produk telah ada di wishlist", "CONFLICT", 409);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationRemoveWishlist(Integer userId, Integer productId) {
        if (!this.isExistWishlist(userId, productId)) {
            return new BasicResult(false, "Wishlist tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationGetAll(Integer userId) {
        if (!this.isExistUser(userId)) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private Boolean isExistUser(Integer id) {
        return userRepository.findFirstById(id) != null;
    }

    private Boolean isExistProduct(Integer id) {
        return productRepository.findFirstById(id) != null;
    }

    private Boolean isExistWishlist(Integer userId, Integer productId) {
        return wishlistRepository.findFirstByUser_IdAndProduct_Id(userId, productId) != null;
    }
}
