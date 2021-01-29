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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
        ArrayList<String> oneWeek = this.getOneWeekDay();

        oneWeek.forEach((val) -> {
            Report2DTO item = this.findDateExist(dto, val);
            Timestamp timestamp = Timestamp.valueOf(val + " 00:00:00");
            Integer income = 0;

            if (item != null) {
                timestamp = Timestamp.valueOf(item.getCreatedAt() + " 00:00:00");
                income = item.getIncome();
            }
            dtos.add(this.mapToReportDTO(FUNC.getFormatDateSlash(timestamp), income));
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

    private ArrayList<String> getOneWeekDay() {
        ArrayList<String> days = new ArrayList<>();
        LocalDate day = LocalDate.now();
        for (int i = 6; i >= 0; i--) {
            String month = String.valueOf(day.getMonthValue());
            if (day.getMonthValue() < 10) {
                month = "0" + day.getMonthValue();
            }
            String date = day.getYear() + "-" + month + "-" + day.minusDays(i).getDayOfMonth();
            days.add(date);
        }
        return days;
    }

    private Report2DTO findDateExist(ArrayList<Report2DTO> dto, String date) {
        for (Report2DTO val : dto) {
            if (String.valueOf(val.getCreatedAt()).equals(date)) {
                return val;
            }
        }
        return null;
    }

}
