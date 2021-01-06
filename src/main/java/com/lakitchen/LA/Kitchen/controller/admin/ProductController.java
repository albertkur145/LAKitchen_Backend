package com.lakitchen.LA.Kitchen.controller.admin;

import com.lakitchen.LA.Kitchen.api.path.admin.ProductPath;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.admin.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductController {

    @Autowired
    private ProductServiceImpl productService;

    @PostMapping(ProductPath.PRODUCT_UPLOAD_PHOTO)
    public ResponseTemplate uploadPhoto(@RequestParam("image") ArrayList<MultipartFile> files) {
        return productService.uploadPhoto(files);
    }

}
