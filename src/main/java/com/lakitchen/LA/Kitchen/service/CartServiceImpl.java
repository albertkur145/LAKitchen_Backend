package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.ProductCartDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductGeneralDTO;
import com.lakitchen.LA.Kitchen.api.requestbody.user.cart.SaveCartRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.user.cart.UpdateCartRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.cart.CountCart;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.cart.GetByUserId;
import com.lakitchen.LA.Kitchen.model.entity.Cart;
import com.lakitchen.LA.Kitchen.repository.CartRepository;
import com.lakitchen.LA.Kitchen.repository.ProductRepository;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import com.lakitchen.LA.Kitchen.service.global.Func;
import com.lakitchen.LA.Kitchen.service.impl.CartService;
import com.lakitchen.LA.Kitchen.service.mapper.ProductMapper;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Func FUNC;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public ResponseTemplate saveProductToCart(SaveCartRequest request) {
        BasicResult result = this.validationSaveProductToCart(request);

        if (result.getResult()) {
            Cart cart;
            if (this.isExistCart(request.getUserId(), request.getProductId())) {
                cart = this.incrementQuantity(request.getUserId(), request.getProductId());
            } else {
                cart = this.setNewCart(request);
            }

            cartRepository.save(cart);
            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate updateCart(UpdateCartRequest request) {
        BasicResult result = this.validationUpdateCart(request);

        if (result.getResult()) {
            Cart cart = cartRepository.findByUser_IdAndProduct_Id(request.getUserId(), request.getProductId());
            cart.setNote(request.getNote());
            cart.setQuantity(request.getQuantity());
            cartRepository.save(cart);

            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate deleteCart(Integer userId, Integer productId) {
        BasicResult result = this.validationDeleteCart(userId, productId);
        if (result.getResult()) {
            cartRepository.deleteByUser_IdAndProduct_Id(userId, productId);
            return new ResponseTemplate(200, "OK", null, null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getAll(Integer userId) {
        BasicResult result = this.validationGetAll(userId);

        if (result.getResult()) {
            ArrayList<Cart> carts = cartRepository.findByUser_Id(userId);
            ArrayList<ProductCartDTO> dto = new ArrayList<>();

            carts.forEach((val) -> {
                dto.add(productMapper.mapToProductCartDTO(val.getProduct(), val));
            });
            return new ResponseTemplate(200, "OK", new GetByUserId(dto), null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate countUserCart(Integer userId) {
        BasicResult result = this.validationCountUserCart(userId);

        if (result.getResult()) {
            Integer countCart = cartRepository.countByUser_Id(userId);
            return new ResponseTemplate(200, "OK", new CountCart(countCart), null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    private Cart setNewCart(SaveCartRequest request) {
        Cart cart = new Cart();
        cart.setNote(request.getNote());
        cart.setQuantity(request.getQuantity());
        cart.setCreatedAt(FUNC.getCurrentTimestamp());
        cart.setUser(userRepository.findFirstById(request.getUserId()));
        cart.setProduct(productRepository.findFirstById(request.getProductId()));
        return cart;
    }

    private Cart incrementQuantity(Integer userId, Integer productId) {
        Cart cart = cartRepository.findByUser_IdAndProduct_Id(userId, productId);
        cart.setQuantity(cart.getQuantity() + 1);
        return cart;
    }

    private BasicResult validationGetAll(Integer userId) {
        if (!FUNC.isExistUser(userId)) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationSaveProductToCart(SaveCartRequest request) {
        if (request.getUserId() == null || request.getProductId() == null
        || request.getQuantity() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!FUNC.isExistUser(request.getUserId())) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }

        if (!FUNC.isExistProduct(request.getProductId())) {
            return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
        }

        if (request.getQuantity() <= 0) {
            return new BasicResult(false, "Jumlah beli minimal 1", "BAD REQUEST", 400);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationUpdateCart(UpdateCartRequest request) {
        if (request.getUserId() == null || request.getProductId() == null
        || request.getQuantity() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!this.isExistCart(request.getUserId(), request.getProductId())) {
            return new BasicResult(false, "Cart tidak ditemukan", "NOT FOUND", 404);
        }

        if (request.getQuantity() <= 0) {
            return new BasicResult(false, "Jumlah beli minimal 1", "BAD REQUEST", 400);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationDeleteCart(Integer userId, Integer productId) {
        if (!this.isExistCart(userId, productId)) {
            return new BasicResult(false, "Cart tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationCountUserCart(Integer userId) {
        if (!FUNC.isExistUser(userId)) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private Boolean isExistCart(Integer userId, Integer productId) {
        return cartRepository.findByUser_IdAndProduct_Id(userId, productId) != null;
    }
}
