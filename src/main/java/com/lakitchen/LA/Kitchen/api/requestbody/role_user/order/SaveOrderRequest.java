package com.lakitchen.LA.Kitchen.api.requestbody.role_user.order;

import com.lakitchen.LA.Kitchen.api.requestbody.role_user.order.helper.ProductHelper;
import lombok.Data;

import java.util.ArrayList;

@Data
public class SaveOrderRequest {

    Integer userId;
    ArrayList<ProductHelper> products;

}
