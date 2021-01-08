package com.lakitchen.LA.Kitchen.api.response.data;

import com.lakitchen.LA.Kitchen.api.response.data.format.GetCategoriesAndSubFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetCategoriesAndSub {
    public ArrayList<GetCategoriesAndSubFormat> categories;
}
