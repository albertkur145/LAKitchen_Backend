package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.api.dto.OrderDetailDTO;
import com.lakitchen.LA.Kitchen.api.dto.OrderGeneralDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductOrderDTO;
import com.lakitchen.LA.Kitchen.model.entity.Order;
import com.lakitchen.LA.Kitchen.model.entity.OrderDetail;
import com.lakitchen.LA.Kitchen.model.entity.OrderStatus;
import com.lakitchen.LA.Kitchen.model.entity.Payment;
import com.lakitchen.LA.Kitchen.service.global.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

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

    public ProductOrderDTO mapToProductOrderDTO(OrderDetail orderDetail) {
        return new ProductOrderDTO(
                orderDetail.getProduct().getId(),
                orderDetail.getProduct().getName(),
                orderDetail.getPrice(),
                orderDetail.getQuantity(),
                orderDetail.getNote(),
                orderDetail.getSubTotal()
        );
    }

    public OrderDetailDTO mapToOrderDetailDTO(Order order, Payment payment, OrderStatus orderStatus,
                                              ArrayList<ProductOrderDTO> products) {
        return new OrderDetailDTO(
                order.getOrderNumber(),
                FUNC.getFormatDateIndonesian(order.getCreatedAt()),
                payment.getTotal(),
                new IdNameDTO(orderStatus.getId(), orderStatus.getName()),
                products
        );
    }

}
