package com.lakitchen.LA.Kitchen.api.requestbody.admin.product;

import lombok.Data;

@Data
public class NewProductRequest {
    String name;
    Integer price;
    String description;
    Integer subCategoryId;
}
