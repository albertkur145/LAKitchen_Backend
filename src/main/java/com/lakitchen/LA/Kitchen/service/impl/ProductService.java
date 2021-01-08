package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.admin.product.NewProductRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

public interface ProductService {

    // ROLE_ADMIN
    ResponseTemplate saveNewProduct(NewProductRequest request);
    ResponseTemplate uploadPhoto(Integer productId, ArrayList<MultipartFile> files);

}
