package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.order.UpdateStatusRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.order.SaveOrderRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface OrderService {

    // ROLE_USER
    ResponseTemplate saveOrder(SaveOrderRequest request);
    ResponseTemplate getAllOrder(Integer userId);
    ResponseTemplate getDetailByOrderNumber(String orderNumber);
    ResponseTemplate getForAssessment(String orderNumber, Integer productId);
    ResponseTemplate cancelOrder(String orderNumber);
    ResponseTemplate getAllForAssessment(String orderNumber);

    // ROLE_ADMIN
    ResponseTemplate getByStatus(Integer page, Integer statusId);
    ResponseTemplate getByStatus(Integer page, int[] statusId);
    ResponseTemplate getById(String orderNumber);
    ResponseTemplate updateStatusOrder(UpdateStatusRequest request);
    ResponseTemplate searchByOrderNumberAndStatus(Integer page, String orderNumber, int[] statusId);

}
