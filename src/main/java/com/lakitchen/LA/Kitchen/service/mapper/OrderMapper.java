package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.api.dto.OrderGeneralDTO;
import com.lakitchen.LA.Kitchen.model.entity.Order;
import com.lakitchen.LA.Kitchen.model.entity.OrderStatus;
import com.lakitchen.LA.Kitchen.model.entity.Payment;
import com.lakitchen.LA.Kitchen.service.global.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderMapper {

    @Autowired
    Func FUNC;

    public OrderGeneralDTO mapToOrderGeneralDTO(Order order, Payment payment, OrderStatus orderStatus) {
        return new OrderGeneralDTO(
                order.getOrderNumber(),
                FUNC.getFormatDateIndonesian(order.getCreatedAt()),
                payment.getTotal(),
                new IdNameDTO(orderStatus.getId(), orderStatus.getName())
        );
    }

}
