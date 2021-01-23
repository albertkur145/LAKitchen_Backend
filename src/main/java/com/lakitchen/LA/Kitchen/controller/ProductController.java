package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.ProductPath;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product.ActivationProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product.NewProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product.UpdateProductRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product.UploadPhotoRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.product.IncrementSeenRequest;
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

    // ROLE_USER
    @GetMapping(ProductPath.GET_PRODUCT_BY_CATEGORY)
    public ResponseTemplate getByCategory(@RequestParam("categoryId") Integer categoryId) {
        return productService.getByCategory(categoryId);
    }

    // ROLE_USER
    @GetMapping(ProductPath.GET_PRODUCT_BY_NAME)
    public ResponseTemplate getByName(@RequestParam("productName") String productName) {
        return productService.getByName(productName);
    }

    // ROLE_USER
    @GetMapping(ProductPath.GET_PRODUCT_BY_PRICE)
    public ResponseTemplate getByPrice(@RequestParam("from") String from) {
        return productService.getByPrice(from);
    }

    // ROLE_USER
    @GetMapping(ProductPath.GET_PRODUCT_BY_ID)
    public ResponseTemplate getById(@RequestParam("productId") Integer productId) {
        return productService.getById(productId);
    }

    // ROLE_USER
    @PutMapping(ProductPath.INCREMENT_SEEN)
    public ResponseTemplate incrementSeen(@RequestBody IncrementSeenRequest objParam) {
        return productService.incrementSeen(objParam);
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

    // ROLE_ADMIN
    @PutMapping(ProductPath.ADMIN_PRODUCT_PUT)
    public ResponseTemplate updateProduct(@RequestBody UpdateProductRequest request) {
        return productService.updateProduct(request);
    }

    // ROLE_ADMIN
    @GetMapping(ProductPath.ADMIN_PRODUCT_GET_ALL)
    public ResponseTemplate getAll(@RequestParam("page") Integer page) {
        return productService.getAll(page);
    }

    // ROLE_ADMIN
    @GetMapping(ProductPath.ADMIN_PRODUCT_GET_TOP_FAVORITE)
    public ResponseTemplate getTopFavorite() {
        return productService.getTopFavorite();
    }

    // ROLE_ADMIN
    @GetMapping(ProductPath.ADMIN_PRODUCT_GET_TOP_SELLING)
    public ResponseTemplate getTopSelling() {
        return productService.getTopSelling();
    }

    // ROLE_ADMIN
    @GetMapping(ProductPath.ADMIN_PRODUCT_GET_TOP_RATING)
    public ResponseTemplate getTopRating() {
        return productService.getTopRating();
    }

    // ROLE_ADMIN
    @GetMapping(ProductPath.ADMIN_PRODUCT_GET_TOP_FAVORITE_BY_CATEGORY)
    public ResponseTemplate getTopFavoriteByCategory(@RequestParam("categoryId") Integer categoryId) {
        return productService.getTopFavoriteByCategory(categoryId);
    }

    // ROLE_ADMIN
    @GetMapping(ProductPath.ADMIN_PRODUCT_GET_TOP_SELLING_BY_CATEGORY)
    public ResponseTemplate getTopSellingByCategory(@RequestParam("categoryId") Integer categoryId) {
        return productService.getTopSellingByCategory(categoryId);
    }

    // ROLE_ADMIN
    @GetMapping(ProductPath.ADMIN_PRODUCT_GET_TOP_RATING_BY_CATEGORY)
    public ResponseTemplate getTopRatingByCategory(@RequestParam("categoryId") Integer categoryId) {
        return productService.getTopRatingByCategory(categoryId);
    }

    // ROLE_ADMIN
    @GetMapping(ProductPath.ADMIN_PRODUCT_GET_ALL_BY_CATEGORY)
    public ResponseTemplate getByCategoryAdmin(@RequestParam("page") Integer page,
            @RequestParam("categoryId") Integer categoryId) {
        return productService.getByCategoryAdmin(page, categoryId);
    }

    // ROLE_ADMIN
    @GetMapping(ProductPath.ADMIN_PRODUCT_GET_BY_ID)
    public ResponseTemplate getByIdAdmin(@RequestParam("id") Integer productId) {
        return productService.getByIdAdmin(productId);
    }

    // ROLE_ADMIN
    @GetMapping(ProductPath.ADMIN_PRODUCT_GET_ALL_BY_NAME)
    public ResponseTemplate getByNameAdmin(@RequestParam("page") Integer page,
                                           @RequestParam("name") String productName) {
        return productService.getByNameAdmin(page, productName);
    }

    // ROLE_ADMIN
    @DeleteMapping(ProductPath.ADMIN_PRODUCT_DELETE)
    public ResponseTemplate deleteProduct(@RequestParam("id") Integer productId) {
        return productService.deleteProduct(productId);
    }

    // ROLE_ADMIN
    @PutMapping(ProductPath.ADMIN_PRODUCT_ACTIVATION)
    public ResponseTemplate activationProduct(@RequestBody ActivationProductRequest objParam) {
        return productService.activationProduct(objParam);
    }

    // ROLE_ADMIN
    @DeleteMapping(ProductPath.ADMIN_PRODUCT_DELETE_PHOTO)
    public ResponseTemplate deletePhoto(@RequestParam("photoId") Integer photoId) {
        return productService.deletePhoto(photoId);
    }
}
