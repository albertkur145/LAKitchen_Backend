package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.ProductPath;
import com.lakitchen.LA.Kitchen.api.requestbody.admin.product.NewProductRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.ProductServiceImpl;
import org.apache.tomcat.util.codec.binary.Base64;
import org.apache.tomcat.util.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    // ROLE_ADMIN
    @PostMapping(value = ProductPath.PRODUCT_UPLOAD_PHOTO)
    public ResponseTemplate uploadPhoto(
            @RequestParam("id") Integer productId,
            @RequestParam("image") ArrayList<MultipartFile> files) {
        return productService.uploadPhoto(productId, files);
    }

    // ROLE_ADMIN
    @PostMapping(ProductPath.PRODUCT_POST)
    public ResponseTemplate saveNewProduct(@RequestBody NewProductRequest objParam) {
        return productService.saveNewProduct(objParam);
    }

    // ROLE_ADMIN
    @GetMapping(value = "/photo")
    public String getImage() throws IOException {
        byte[] bytes =  productService.getImageWithMediaType("13c7ae5d-0d0f-4aa3-a605-9ee96cdae370.jpg");

        StringBuilder builder = new StringBuilder();
        builder.append("data:image/jpg;base64,");
        builder.append(StringUtils.newStringUtf8(Base64.encodeBase64(bytes, false)));

        return builder.toString();
//        return Base64.getUrlEncoder().encodeToString(bytes);
    }

}
