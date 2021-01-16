package com.lakitchen.LA.Kitchen.api.requestbody.role_admin.product;

import lombok.Data;

@Data
public class NewProductRequest {
    String name;
    Integer price;
    String description;
    Integer subCategoryId;
}
