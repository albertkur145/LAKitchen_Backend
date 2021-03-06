package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.*;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.dashboard.GetAll;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.report.GetAnnualIncome;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.report.GetMonthlyIncome;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.report.GetSalesToday;
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

        ArrayList<Order> orders = orderRepository.findByFinishedDate();
        Integer todayIncome = reportMapper.getIncome(orders);
        Integer weekIncome = reportMapper.getIncome(orderRepository.findByLastWeek());
        Integer productSoldToday = reportMapper.getQuantityOrderProduct(orders);

        Integer newUsers = userRepository.countNewUsers();
        Integer allUser = userRepository.countAllUser();
        ArrayList<Report2DTO> payments = paymentRepository.findLastWeek();

        DashboardOrderDTO orderDTO = reportMapper.mapToDashboardOrderDTO(orderToday, orderPrepared, orderReadyToShip, orderInDelivery);
        DashboardSalesDTO salesDTO = reportMapper.mapToDashboardSalesDTO(todayIncome, weekIncome, productSoldToday);
        DashboardOthersDTO othersDTO = reportMapper.mapToDashboardOthersDTO(newUsers, allUser);
        ArrayList<ReportDTO> weeklyReport = reportMapper.helperMapFromReport2DTOWeekly(payments);

        return new ResponseTemplate(200, "OK",
                new GetAll(orderDTO, salesDTO, othersDTO, weeklyReport),
                null, null);
    }

    @Override
    public ResponseTemplate getSalesToday() {
        ArrayList<SalesTodayDTO> dto = orderDetailRepository.findSalesToday();
        return new ResponseTemplate(200, "OK", new GetSalesToday(dto), null, null);
    }

    @Override
    public ResponseTemplate getMonthlyIncome(String year, String month) {
        ArrayList<Report2DTO> payments = paymentRepository
                .findMonthly(Integer.parseInt(year), Integer.parseInt(month));
        ArrayList<ReportDTO> monthlyReport = reportMapper.helperMapFromReport2DTOMonthly(payments);
        Integer totalIncome = reportMapper.getTotalIncome(payments);

        return new ResponseTemplate(200, "OK",
                new GetMonthlyIncome(monthlyReport, totalIncome),
                null, null);
    }

    @Override
    public ResponseTemplate getAnnualIncome(String year) {
        ArrayList<Report3DTO> payments = paymentRepository
                .findAnnual(Integer.parseInt(year));
        ArrayList<ReportAnnualDTO> annualReport = reportMapper.helperMapFromReport3DTOMonthly(payments);
        Integer totalIncome = reportMapper.getTotalIncome2(payments);

        return new ResponseTemplate(200, "OK",
                new GetAnnualIncome(annualReport, totalIncome),
                null, null);
    }
}
