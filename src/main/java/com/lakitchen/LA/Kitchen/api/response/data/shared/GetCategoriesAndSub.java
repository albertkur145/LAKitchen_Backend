package com.lakitchen.LA.Kitchen.api.response.data.shared;

import com.lakitchen.LA.Kitchen.api.response.data.shared.format.GetCategoriesAndSubFormat;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetCategoriesAndSub {
    public ArrayList<GetCategoriesAndSubFormat> categories;
}
