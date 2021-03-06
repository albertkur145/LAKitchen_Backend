package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.ProductCategoryPath;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.ProductCategoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.security.RolesAllowed;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ProductCategoryController {

    @Autowired
    private ProductCategoryServiceImpl productCategoryService;

    @GetMapping(ProductCategoryPath.CATEGORY_GET_ALL)
    public ResponseTemplate getCategories() {
        return productCategoryService.getCategories();
    }

    @GetMapping(ProductCategoryPath.CATEGORY_GET_ALL_WITH_SUB)
    public ResponseTemplate getCategoriesAndSub() {
        return productCategoryService.getCategoriesAndSub();
    }

    @PostMapping(ProductCategoryPath.CATEGORY_RESET)
    public ResponseTemplate resetData() {
        return productCategoryService.resetData();
    }
}
