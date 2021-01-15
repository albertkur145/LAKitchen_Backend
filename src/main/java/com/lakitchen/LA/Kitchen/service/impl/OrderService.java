package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.user.order.SaveOrderRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface OrderService {

    ResponseTemplate saveOrder(SaveOrderRequest request);
    ResponseTemplate getAllOrder(Integer userId);
    ResponseTemplate getDetailByOrderNumber(String orderNumber);
    ResponseTemplate getForAssessment(String orderNumber, Integer productId);
    ResponseTemplate cancelOrder(String orderNumber);

}
