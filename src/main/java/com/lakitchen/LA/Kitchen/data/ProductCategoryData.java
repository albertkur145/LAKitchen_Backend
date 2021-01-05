package com.lakitchen.LA.Kitchen.data;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProductCategoryData {
    String name;
    ProductSubCategoryData[] subCategoryData;
}
