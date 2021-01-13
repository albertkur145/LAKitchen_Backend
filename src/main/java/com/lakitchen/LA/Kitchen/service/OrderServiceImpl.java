package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.requestbody.user.order.SaveOrderRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.user.order.helper.ProductHelper;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.model.entity.Order;
import com.lakitchen.LA.Kitchen.repository.OrderRepository;
import com.lakitchen.LA.Kitchen.repository.OrderStatusRepository;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import com.lakitchen.LA.Kitchen.service.global.Func;
import com.lakitchen.LA.Kitchen.service.impl.OrderService;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
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
            orderRepository.save(order);
            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
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
