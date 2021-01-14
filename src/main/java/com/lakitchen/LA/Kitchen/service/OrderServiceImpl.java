package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.OrderGeneralDTO;
import com.lakitchen.LA.Kitchen.api.requestbody.user.order.SaveOrderRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.user.order.helper.ProductHelper;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.order.GetById;
import com.lakitchen.LA.Kitchen.model.entity.*;
import com.lakitchen.LA.Kitchen.repository.*;
import com.lakitchen.LA.Kitchen.service.global.Func;
import com.lakitchen.LA.Kitchen.service.impl.OrderService;
import com.lakitchen.LA.Kitchen.service.mapper.OrderMapper;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;


@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    PaymentMethodRepository paymentMethodRepository;

    @Autowired
    OrderMapper orderMapper;

    @Autowired
    Func FUNC;

    @Override
    public ResponseTemplate saveOrder(SaveOrderRequest request) {
        BasicResult result = this.validationSaveOrder(request);

        if (result.getResult()) {
            Order order = new Order();
            order.setOrderNumber(this.setOrderNumber());
            order.setCreatedAt(FUNC.getCurrentTimestamp());
            order.setOrderStatus(orderStatusRepository.findFirstById(1));
            order.setUser(userRepository.findFirstById(request.getUserId()));
            Order orderSaved = orderRepository.save(order);

            final Integer[] totalPayment = {0};
            request.getProducts().forEach((val) -> {
                OrderDetail orderDetail = new OrderDetail();
                Product product = productRepository.findFirstById(val.getId());
                Integer subTotal = product.getPrice() * val.getQuantity();
                totalPayment[0] = totalPayment[0] + subTotal;

                orderDetail.setIsAssessment(0);
                orderDetail.setNote(val.getNote());
                orderDetail.setPrice(product.getPrice());
                orderDetail.setQuantity(val.getQuantity());
                orderDetail.setSubTotal(subTotal);
                orderDetail.setProduct(product);
                orderDetail.setOrder(orderSaved);
                orderDetailRepository.save(orderDetail);
            });

            Payment payment = new Payment();
            payment.setOrder(orderSaved);
            payment.setTotal(totalPayment[0]);
            payment.setCreatedAt(FUNC.getCurrentTimestamp());
            payment.setPaymentMethod(paymentMethodRepository.findFirstById(1));
            paymentRepository.save(payment);

            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getAllOrder(Integer userId) {
        BasicResult result = this.validationGetAllOrder(userId);

        if (result.getResult()) {
            ArrayList<Order> orders = orderRepository.findByUser_Id(userId);

            ArrayList<OrderGeneralDTO> dtos = new ArrayList<>();
            orders.forEach((val) -> {
                OrderStatus status = orderStatusRepository.findFirstById(val.getOrderStatus().getId());
                Payment payment = paymentRepository.findFirstByOrder_OrderNumber(val.getOrderNumber());
                dtos.add(orderMapper.mapToOrderGeneralDTO(val, payment, status));
            });

            return new ResponseTemplate(200, "OK", new GetById(dtos), null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    private BasicResult validationGetAllOrder(Integer userId) {
        if (!FUNC.isExistUser(userId)) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationSaveOrder(SaveOrderRequest request) {
        if (request.getUserId() == null || request.getProducts().size() == 0) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!FUNC.isExistUser(request.getUserId())) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }

        for (ProductHelper val : request.getProducts()) {
            if (val.getId() == null || val.getQuantity() == null) {
                return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
            }

            if (val.getQuantity() <= 0) {
                return new BasicResult(false, "Jumlah beli minimal 1", "BAD REQUEST", 400);
            }

            if (!FUNC.isExistProduct(val.getId())) {
                return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
            }
        }

        return new BasicResult(true, null, "OK", 200);
    }


    private Boolean isExistOrder(String orderNumber) {
        return orderRepository.findFirstByOrderNumber(orderNumber) != null;
    }

    private String setOrderNumber() {
        String orderNum = this.generateStringOrder();
        while (this.isExistOrder(orderNum)) {
            orderNum = this.generateStringOrder();
        }
        return orderNum;
    }

    private String generateStringOrder() {
        String date = new SimpleDateFormat("ddMMyyyy").format(new Date());
        return 10000 + new Random().nextInt(90000) + date;
    }
}
