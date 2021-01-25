package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.DashboardOrderDTO;
import com.lakitchen.LA.Kitchen.api.dto.DashboardOthersDTO;
import com.lakitchen.LA.Kitchen.api.dto.DashboardSalesDTO;
import com.lakitchen.LA.Kitchen.api.dto.ReportDTO;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.dashboard.GetAll;
import com.lakitchen.LA.Kitchen.model.entity.Order;
import com.lakitchen.LA.Kitchen.model.entity.Payment;
import com.lakitchen.LA.Kitchen.repository.OrderDetailRepository;
import com.lakitchen.LA.Kitchen.repository.OrderRepository;
import com.lakitchen.LA.Kitchen.repository.PaymentRepository;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import com.lakitchen.LA.Kitchen.service.global.Func;
import com.lakitchen.LA.Kitchen.service.impl.ReportService;
import com.lakitchen.LA.Kitchen.service.mapper.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ReportServiceImpl implements ReportService {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ReportMapper reportMapper;

    @Autowired
    Func FUNC;

    @Override
    public ResponseTemplate getAllDashboard() {
        Integer orderToday = orderRepository.countOrderToday();
        Integer orderPrepared = orderRepository.countByOrderStatus_Id(2);
        Integer orderReadyToShip = orderRepository.countByOrderStatus_Id(3);
        Integer orderInDelivery = orderRepository.countByOrderStatus_Id(4);

        ArrayList<Order> orders = orderRepository.findByCurrentDate(5);
        Integer todayIncome = reportMapper.getIncome(orders);
        Integer weekIncome = reportMapper.getIncome(orderRepository.findByLastWeek(5));
        Integer productSoldToday = reportMapper.getQuantityOrderProduct(orders);

        Integer newUsers = userRepository.countNewUsers();
        Integer allUser = (int) userRepository.count();
        ArrayList<Payment> payments = paymentRepository.findLastWeek(5);

        DashboardOrderDTO orderDTO = reportMapper.mapToDashboardOrderDTO(orderToday, orderPrepared, orderReadyToShip, orderInDelivery);
        DashboardSalesDTO salesDTO = reportMapper.mapToDashboardSalesDTO(todayIncome, weekIncome, productSoldToday);
        DashboardOthersDTO othersDTO = reportMapper.mapToDashboardOthersDTO(newUsers, allUser);
        ArrayList<ReportDTO> weeklyReport = reportMapper.helperMapReportDTO(payments);

        return new ResponseTemplate(200, "OK",
                new GetAll(orderDTO, salesDTO, othersDTO, weeklyReport),
                null, null);
    }
}
