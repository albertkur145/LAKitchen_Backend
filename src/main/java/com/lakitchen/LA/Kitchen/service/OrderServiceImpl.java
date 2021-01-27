package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.*;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.order.UpdateStatusRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.order.SaveOrderRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.order.helper.ProductHelper;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.order.GetById;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.order.GetByStatus;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.order.GetUnprocessed;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.order.GetAllForAssessment;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.order.GetByUserId;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.order.GetDetailByNumber;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.order.GetForAssessment;
import com.lakitchen.LA.Kitchen.model.entity.*;
import com.lakitchen.LA.Kitchen.repository.*;
import com.lakitchen.LA.Kitchen.service.global.Func;
import com.lakitchen.LA.Kitchen.service.impl.OrderService;
import com.lakitchen.LA.Kitchen.service.mapper.OrderMapper;
import com.lakitchen.LA.Kitchen.service.mapper.ProductMapper;
import com.lakitchen.LA.Kitchen.service.mapper.UserMapper;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
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
    ProductMapper productMapper;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CartRepository cartRepository;

    @Autowired
    Func FUNC;

    @Override
    public ResponseTemplate saveOrder(SaveOrderRequest request) {
        BasicResult result = this.validationSaveOrder(request);

        if (result.getResult()) {
            Order order = new Order();
            User user = userRepository.findFirstById(request.getUserId());

            order.setOrderNumber(this.setOrderNumber());
            order.setCreatedAt(FUNC.getCurrentTimestamp());
            order.setOrderStatus(orderStatusRepository.findFirstById(1));
            order.setUser(user);
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
                cartRepository.deleteByUser_IdAndProduct_Id(user.getId(), product.getId());
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
            ArrayList<Order> orders = orderRepository.findByUser_IdOrderByCreatedAtDesc(userId);

            ArrayList<OrderGeneralDTO> dtos = new ArrayList<>();
            orders.forEach((val) -> {
                Payment payment = paymentRepository.findFirstByOrder_OrderNumber(val.getOrderNumber());
                dtos.add(orderMapper.mapToOrderGeneralDTO(val, payment, val.getOrderStatus()));
            });

            return new ResponseTemplate(200, "OK", new GetByUserId(dtos), null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getDetailByOrderNumber(String orderNumber) {
        BasicResult result = this.validationGetDetailByOrderNumber(orderNumber);

        if (result.getResult()) {
            Order order = orderRepository.findFirstByOrderNumber(orderNumber);
            Payment payment = paymentRepository.findFirstByOrder_OrderNumber(orderNumber);
            ArrayList<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderNumber(orderNumber);
            ArrayList<ProductOrderDTO> productOrderDTOS = this.helperMapToProductOrder(orderDetails);

            return new ResponseTemplate(200, "OK",
                    new GetDetailByNumber(orderMapper.mapToOrderDetailDTO(order,
                            payment, order.getOrderStatus(), productOrderDTOS)),
                    null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getForAssessment(String orderNumber, Integer productId) {
        BasicResult result = this.validationGetForAssessment(orderNumber, productId);

        if (result.getResult()) {
            Order order = orderRepository.findFirstByOrderNumber(orderNumber);
            Product product = productRepository.findFirstById(productId);
            ProductSimplifiedDTO productDTO = productMapper.mapToProductSimplifiedDTO(product);

            return new ResponseTemplate(200, "OK",
                    new GetForAssessment(order.getOrderNumber(),
                            FUNC.getFormatDateIndonesian(order.getCreatedAt()), productDTO),
                    null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate cancelOrder(String orderNumber) {
        BasicResult result = this.validationCancelOrder(orderNumber);

        if (result.getResult()) {
            Order order = orderRepository.findFirstByOrderNumber(orderNumber);
            OrderStatus orderStatus = orderStatusRepository.findFirstById(6);
            order.setOrderStatus(orderStatus);
            orderRepository.save(order);
            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getAllForAssessment(String orderNumber) {
        BasicResult result = this.validationGetAllForAssessment(orderNumber);

        if (result.getResult()) {
            Order order = orderRepository.findFirstByOrderNumber(orderNumber);
            Payment payment = paymentRepository.findFirstByOrder_OrderNumber(orderNumber);
            ArrayList<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderNumber(orderNumber);
            ArrayList<ProductOrder2DTO> productDTOS = new ArrayList<>();
            OrderGeneralDTO orderDTO = orderMapper.mapToOrderGeneralDTO(order, payment, order.getOrderStatus());

            orderDetails.forEach((val) -> {
                productDTOS.add(productMapper.mapToProductOrder2DTO(val));
            });

            return new ResponseTemplate(200, "OK",
                    new GetAllForAssessment(orderDTO, productDTOS),
                    null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getByStatus(Integer page, Integer statusId) {
        Pageable paging = PageRequest.of((page-1), 10, Sort.by("createdAt").descending());
        Page<Order> orders = orderRepository.findByOrderStatus_Id(paging, statusId);
        ArrayList<OrderGeneralDTO> dto = this.helperMapToOrderGeneralDTO(orders);
        PageableDTO pageableDTO = FUNC.mapToPageableDTO(orders);
        return new ResponseTemplate(200, "OK", new GetByStatus(dto), pageableDTO, null);
    }

    @Override
    public ResponseTemplate getById(String orderNumber) {
        BasicResult result = this.validationGetById(orderNumber);

        if (result.getResult()) {
            Order order = orderRepository.findFirstByOrderNumber(orderNumber);
            Payment payment = paymentRepository.findFirstByOrder_OrderNumber(orderNumber);
            User user = userRepository.findFirstById(order.getUser().getId());
            ArrayList<OrderDetail> orderDetails = orderDetailRepository.findByOrder_OrderNumber(orderNumber);

            OrderAdminDTO orderDTO = orderMapper.mapToOrderAdminDTO(order, payment);
            UserDTO userDTO = userMapper.mapToUserDTO(user);
            ArrayList<ProductOrderDTO> productOrderDTOS = this.helperMapToProductOrder(orderDetails);
            return new ResponseTemplate(200, "OK",
                    new GetById(userDTO, orderDTO, productOrderDTOS),
                    null, result.getError());

        }
        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate updateStatusOrder(UpdateStatusRequest request) {
        BasicResult result = this.validateUpdateStatusOrder(request);

        if (result.getResult()) {
            Order order = orderRepository.findFirstByOrderNumber(request.getOrderNumber());
            order.setOrderStatus(orderStatusRepository.findFirstById(request.getOrderStatusId()));
            orderRepository.save(order);
            return new ResponseTemplate(200, "OK", null, null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate searchByOrderNumberAndStatus(Integer page, String orderNumber, int[] statusId) {
        Pageable paging = PageRequest.of((page-1), 10, Sort.by("createdAt").descending());
        ArrayList<OrderStatus> orderStatus = this.getMultipleOrderStatus(statusId);
        Page<Order> orders = orderRepository
                .findByOrderNumberContainingAndOrderStatusIn(paging, orderNumber, orderStatus);

        ArrayList<OrderGeneralDTO> dto = this.helperMapToOrderGeneralDTO(orders);
        PageableDTO pageableDTO = FUNC.mapToPageableDTO(orders);
        return new ResponseTemplate(200, "OK", dto, pageableDTO, null);
    }

    private ArrayList<OrderGeneralDTO> helperMapToOrderGeneralDTO(Page<Order> orders) {
        ArrayList<OrderGeneralDTO> dto = new ArrayList<>();
        orders.getContent().forEach((val) -> {
            dto.add(orderMapper.mapToOrderGeneralDTO(val, val.getPayment(), val.getOrderStatus()));
        });
        return dto;
    }
    
    private ArrayList<OrderStatus> getMultipleOrderStatus(int[] statusId) {
        ArrayList<OrderStatus> status = new ArrayList<>();
        for (int i = 0; i < statusId.length; i++) {
            status.add(orderStatusRepository.findFirstById(statusId[i]));
        }
        return status;
    }

    private BasicResult validateUpdateStatusOrder(UpdateStatusRequest request) {
        if (request.getOrderNumber() == null || request.getOrderStatusId() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!this.isExistOrder(request.getOrderNumber())) {
            return new BasicResult(false, "Order tidak ditemukan", "NOT FOUND", 404);
        }

        if (!this.isExistOrderStatus(request.getOrderStatusId())) {
            return new BasicResult(false, "Status order tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private ArrayList<ProductOrderDTO> helperMapToProductOrder(ArrayList<OrderDetail> orderDetails) {
        ArrayList<ProductOrderDTO> dto = new ArrayList<>();
        orderDetails.forEach((val) -> {
            dto.add(orderMapper.mapToProductOrderDTO(val));
        });
        return dto;
    }

    private BasicResult validationGetById(String orderNumber) {
        if (!this.isExistOrder(orderNumber)) {
            return new BasicResult(false, "Order tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationGetAllForAssessment(String orderNumber) {
        if (!this.isFinishedOrder(orderNumber)) {
            return new BasicResult(false, "Order tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationCancelOrder(String orderNumber) {
        if (!this.isExistOrder(orderNumber)) {
            return new BasicResult(false, "Order tidak ditemukan", "NOT FOUND", 404);
        }

        if (!this.isUnprocessedOrder(orderNumber)) {
            return new BasicResult(false, "Order tidak dapat dibatalkan", "NOT ACCEPTABLE", 406);
        }

        return new BasicResult(true, null, "OK", 200);
    }


    private BasicResult validationGetForAssessment(String orderNumber, Integer productId) {
        if (!this.isExistOrder(orderNumber)) {
            return new BasicResult(false, "Order tidak ditemukan", "NOT FOUND", 404);
        }

        if (!FUNC.isExistProduct(productId)) {
            return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }


    private BasicResult validationGetDetailByOrderNumber(String orderNumber) {
        if (!this.isExistOrder(orderNumber)) {
            return new BasicResult(false, "Order tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
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

    private Boolean isExistOrderStatus(Integer id) {
        return orderStatusRepository.findFirstById(id) != null;
    }

    private Boolean isExistOrder(String orderNumber) {
        return orderRepository.findFirstByOrderNumber(orderNumber) != null;
    }

    private Boolean isUnprocessedOrder(String orderNumber) {
        return orderRepository.findFirstByOrderNumberAndOrderStatus_Id(orderNumber, 1) != null;
    }

    private Boolean isFinishedOrder(String orderNumber) {
        return orderRepository.findFirstByOrderNumberAndOrderStatus_Id(orderNumber, 5) != null;
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
