package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface ProductCategoryService {

    ResponseTemplate resetData();
    ResponseTemplate getCategories();
    ResponseTemplate getCategoriesAndSub();

}
