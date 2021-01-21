package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product.NewProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product.UpdateProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.product.IncrementSeenRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface ProductService {

    // ROLE_USER
    ResponseTemplate getBySubCategory(Integer subCategoryId);
    ResponseTemplate getByCategory(Integer categoryId);
    ResponseTemplate getByName(String productName);
    ResponseTemplate getByPrice(String from);
    ResponseTemplate getById(Integer productId);
    ResponseTemplate incrementSeen(IncrementSeenRequest request);

    // ROLE_ADMIN
    ResponseTemplate uploadPhoto(Integer productId, ArrayList<MultipartFile> files);
    ResponseTemplate saveNewProduct(NewProductRequest request);
    ResponseTemplate updateProduct(UpdateProductRequest request);
    ResponseTemplate getAll(Integer page);
    ResponseTemplate getTopFavorite();
    ResponseTemplate getTopSelling();
    ResponseTemplate getTopRating();
    ResponseTemplate getTopFavoriteByCategory(Integer categoryId);
    ResponseTemplate getTopSellingByCategory(Integer categoryId);

}
