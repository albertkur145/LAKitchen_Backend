package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.ProductPath;
import com.lakitchen.LA.Kitchen.api.requestbody.admin.product.NewProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.admin.product.UpdateProductRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    // ROLE_USER
    @GetMapping(ProductPath.GET_PRODUCT_BY_SUBCATEGORY)
    public ResponseTemplate getBySubCategory(@RequestParam("subCategoryId") Integer subCategoryId) {
        return productService.getBySubCategory(subCategoryId);
    }

    // ROLE_ADMIN
    @PostMapping(ProductPath.ADMIN_PRODUCT_UPLOAD_PHOTO)
    public ResponseTemplate uploadPhoto(
            @RequestParam("productId") Integer productId,
            @RequestParam("images") ArrayList<MultipartFile> files) {
        return productService.uploadPhoto(productId, files);
    }

    // ROLE_ADMIN
    @PostMapping(ProductPath.ADMIN_PRODUCT_POST)
    public ResponseTemplate saveNewProduct(@RequestBody NewProductRequest objParam) {
        return productService.saveNewProduct(objParam);
    }

    @PutMapping(ProductPath.ADMIN_PRODUCT_PUT)
    public ResponseTemplate updateProduct(@RequestBody UpdateProductRequest request) {
        return productService.updateProduct(request);
    }

}
