package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.OrderPath;
import com.lakitchen.LA.Kitchen.api.requestbody.user.order.SaveOrderRequest;
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

}
