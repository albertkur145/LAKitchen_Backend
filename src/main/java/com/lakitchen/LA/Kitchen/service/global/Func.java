package com.lakitchen.LA.Kitchen.service.global;

import com.lakitchen.LA.Kitchen.repository.ProductRepository;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Calendar;

@Service
public class Func {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProductRepository productRepository;

    public Boolean isExistUser(Integer id) {
        return userRepository.findFirstById(id) != null;
    }

    public Boolean isExistProduct(Integer id) {
        return productRepository.findFirstById(id) != null;
    }

    public Timestamp getCurrentTimestamp() {
        return new Timestamp(System.currentTimeMillis());
    }

    public String getFormatDateIndonesian(Timestamp param) {
        LocalDateTime local = param.toLocalDateTime();
        String day = String.valueOf(local.getDayOfMonth());
        String month = this.getMonthIndonesian(local.getMonthValue());
        String year = String.valueOf(local.getYear());

        return day + " " + month + " " + year;
    }

    private String getMonthIndonesian(Integer month) {
        switch (month) {
            case 1:
                return "Januari";
            case 2:
                return "Februari";
            case 3:
                return "Maret";
            case 4:
                return "April";
            case 5:
                return "Mei";
            case 6:
                return "Juni";
            case 7:
                return "Juli";
            case 8:
                return "Agustus";
            case 9:
                return "September";
            case 10:
                return "Oktober";
            case 11:
                return "November";
            case 12:
                return "Desember";
            default:
                return String.valueOf(month);
        }
    }

}