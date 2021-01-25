package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.OrderPath;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.order.UpdateStatusRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.order.SaveOrderRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

    @Autowired
    OrderServiceImpl orderService;

    // ROLE_USER
    @PostMapping(OrderPath.ORDER_POST)
    public ResponseTemplate saveOrder(@RequestBody SaveOrderRequest objParam) {
        return orderService.saveOrder(objParam);
    }

    // ROLE_USER
    @GetMapping(OrderPath.ORDER_GET_ALL)
    public ResponseTemplate getAllOrder(@RequestParam("userId") Integer userId) {
        return orderService.getAllOrder(userId);
    }

    // ROLE_USER
    @GetMapping(OrderPath.ORDER_GET_BY_ORDER_NUMBER)
    public ResponseTemplate getDetailByOrderNumber(@RequestParam("orderNumber") String orderNumber) {
        return orderService.getDetailByOrderNumber(orderNumber);
    }

    // ROLE_USER
    @GetMapping(OrderPath.ORDER_GET_FOR_ASSESSMENT)
    public ResponseTemplate getForAssessment(@RequestParam("orderNumber") String orderNumber,
                                             @RequestParam("productId") Integer productId) {
        return orderService.getForAssessment(orderNumber, productId);
    }

    // ROLE_USER
    @GetMapping(OrderPath.ORDER_GET_PRODUCTS_FOR_ASSESSMENT)
    public ResponseTemplate getAllForAssessment(@RequestParam("orderNumber") String orderNumber) {
        return orderService.getAllForAssessment(orderNumber);
    }

    // ROLE_USER
    @DeleteMapping(OrderPath.ORDER_DELETE)
    public ResponseTemplate cancelOrder(@RequestParam("orderNumber") String orderNumber) {
        return orderService.cancelOrder(orderNumber);
    }

    // ROLE_ADMIN
    @GetMapping(OrderPath.ADMIN_GET_BY_STATUS)
    public ResponseTemplate getByStatus(@RequestParam("page") Integer page,
                                        @RequestParam("orderStatusId") Integer statusId) {
        return orderService.getByStatus(page, statusId);
    }

    // ROLE_ADMIN
    @GetMapping(OrderPath.ADMIN_GET_DETAIL_ORDER)
    public ResponseTemplate getById(@RequestParam("orderNumber") String orderNumber) {
        return orderService.getById(orderNumber);
    }

    // ROLE_ADMIN
    @PutMapping(OrderPath.ADMIN_UPDATE_STATUS_ORDER)
    public ResponseTemplate updateStatusOrder(@RequestBody UpdateStatusRequest objParam) {
        return orderService.updateStatusOrder(objParam);
    }

    // ROLE_ADMIN
    @GetMapping(OrderPath.ADMIN_CONFIRM_UNPROCESSED)
    public ResponseTemplate confirmUnprocessed() {
        return orderService.confirmUnprocessed();
    }

    // ROLE_ADMIN
    @GetMapping(OrderPath.ADMIN_GET_UNPROCESSED)
    public ResponseTemplate getUnprocessed() {
        return orderService.getUnprocessed();
    }

    // ROLE_ADMIN
    @GetMapping(OrderPath.ADMIN_SEARCH_UNPROCESSED)
    public ResponseTemplate searchUnprocessed() {
        return orderService.searchUnprocessed();
    }

}
