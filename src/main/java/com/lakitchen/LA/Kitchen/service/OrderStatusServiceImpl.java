package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.order.status.GetActiveStatus;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.order.status.GetFinishedStatus;
import com.lakitchen.LA.Kitchen.model.entity.OrderStatus;
import com.lakitchen.LA.Kitchen.repository.OrderStatusRepository;
import com.lakitchen.LA.Kitchen.service.impl.OrderStatusService;
import com.lakitchen.LA.Kitchen.service.mapper.OrderStatusMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderStatusServiceImpl implements OrderStatusService {

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    OrderStatusMapper orderStatusMapper;

    @Override
    public ResponseTemplate getActiveStatus() {
        int[] status = {2, 3, 4};
        ArrayList<OrderStatus> orderStatus = orderStatusRepository.findByIdIn(status);
        ArrayList<IdNameDTO> dto = orderStatusMapper.helperMapOrderStatus(orderStatus);
        return new ResponseTemplate(200, "OK",
                new GetActiveStatus(dto), null, null);
    }

    @Override
    public ResponseTemplate getFinishedStatus() {
        int[] status = {5, 6};
        ArrayList<OrderStatus> orderStatus = orderStatusRepository.findByIdIn(status);
        ArrayList<IdNameDTO> dto = orderStatusMapper.helperMapOrderStatus(orderStatus);
        return new ResponseTemplate(200, "OK",
                new GetFinishedStatus(dto), null, null);
    }
}
