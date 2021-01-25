package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.*;
import com.lakitchen.LA.Kitchen.model.entity.Order;
import com.lakitchen.LA.Kitchen.model.entity.OrderDetail;
import com.lakitchen.LA.Kitchen.model.entity.Payment;
import com.lakitchen.LA.Kitchen.repository.OrderDetailRepository;
import com.lakitchen.LA.Kitchen.repository.PaymentRepository;
import com.lakitchen.LA.Kitchen.service.global.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;

@Service
public class ReportMapper {

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    Func FUNC;

    public DashboardOrderDTO mapToDashboardOrderDTO(Integer today, Integer prepared,
                                                    Integer readyToShip, Integer inDelivery) {
        return new DashboardOrderDTO(today, prepared, readyToShip, inDelivery);
    }

    public DashboardSalesDTO mapToDashboardSalesDTO(Integer todayIncome, Integer weekIncome, Integer productSoldToday) {
        return new DashboardSalesDTO(todayIncome, weekIncome, productSoldToday);
    }

    public DashboardOthersDTO mapToDashboardOthersDTO(Integer newUsers, Integer allUser) {
        return new DashboardOthersDTO(newUsers, allUser);
    }

    public ReportDTO mapToReportDTO(String date, Integer income) {
        return new ReportDTO(date, income);
    }

    public ArrayList<ReportDTO> helperMapReportDTO(ArrayList<Payment> payments) {
        ArrayList<ReportDTO> dtos = new ArrayList<>();
        payments.forEach((val) -> {
            dtos.add(this.mapToReportDTO(FUNC.getFormatDateSlash(val.getCreatedAt()), val.getTotal()));
        });
        return dtos;
    }

    public ArrayList<ReportDTO> helperMapFromReport2DTO(ArrayList<Report2DTO> dto) {
        ArrayList<ReportDTO> dtos = new ArrayList<>();
        dto.forEach((val) -> {
            Timestamp timestamp = Timestamp.valueOf(val.getCreatedAt() + " 00:00:00");
            dtos.add(this.mapToReportDTO(FUNC.getFormatDateSlash(timestamp), val.getIncome()));
        });
        return dtos;
    }

    public Integer getIncome(ArrayList<Order> orders) {
        int[] income = {0};
        orders.forEach((val) -> {
            Payment payment = paymentRepository.findFirstByOrder_OrderNumber(val.getOrderNumber());
            income[0] += payment.getTotal();
        });
        return income[0];
    }

    public Integer getQuantityOrderProduct(ArrayList<Order> orders) {
        int[] qty = {0};
        orders.forEach((val) -> {
            qty[0] += orderDetailRepository.sumQuantityByOrderNumber(val.getOrderNumber());
        });
        return qty[0];
    }

}
