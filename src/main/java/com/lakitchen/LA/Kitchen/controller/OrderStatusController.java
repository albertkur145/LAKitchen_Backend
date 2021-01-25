package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.OrderStatusPath;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.OrderStatusServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class OrderStatusController {

    @Autowired
    OrderStatusServiceImpl orderStatusService;

    @GetMapping(OrderStatusPath.GET_ACTIVE_STATUS)
    public ResponseTemplate getActiveStatus() {
        return orderStatusService.getActiveStatus();
    }

    @GetMapping(OrderStatusPath.GET_FINISHED_STATUS)
    public ResponseTemplate getFinishedStatus() {
        return orderStatusService.getFinishedStatus();
    }

}
