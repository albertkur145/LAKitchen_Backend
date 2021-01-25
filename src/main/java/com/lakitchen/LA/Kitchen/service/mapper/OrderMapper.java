package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.*;
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

    public OrderAdminDTO mapToOrderAdminDTO(Order order, Payment payment) {
        return new OrderAdminDTO(
                order.getOrderNumber(),
                FUNC.getFormatDateIndonesian(order.getCreatedAt()),
                order.getOrderStatus().getName(),
                payment.getPaymentMethod().getName(),
                payment.getTotal()
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
