package com.lakitchen.LA.Kitchen.service.global;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.api.dto.PageableDTO;
import com.lakitchen.LA.Kitchen.repository.ProductRepository;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

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

    public String getFormatDateSlash(Timestamp param) {
        ZonedDateTime dateTime = this.getJakartaTimeZone(param);
        String[] strings = String.valueOf(dateTime).split("T");
        String[] date = strings[0].split("-");
        return date[2] + "/" + date[1] + "/" + date[0];
    }

    public String getFormatDateSimplified(Timestamp param) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
        return dateFormat.format(param);
    }

    public String currencyIndonesian(Integer number) {
        DecimalFormat decimalFormat = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols format = new DecimalFormatSymbols();
        format.setCurrencySymbol("Rp ");
        format.setGroupingSeparator('.');
        decimalFormat.setDecimalFormatSymbols(format);

        return decimalFormat.format(number);
    }

    public PageableDTO mapToPageableDTO(Page<?> data) {
        return new PageableDTO((data.getNumber()+1),
                (int) data.getTotalElements(), data.getSize());
    }

    public IdNameDTO mapToIdNameDTO(Integer id, String name) {
        return new IdNameDTO(id, name);
    }

    public String getFormatDateIndonesian(Timestamp param) {
        ZonedDateTime dateTime = this.getJakartaTimeZone(param);
        LocalDateTime local = dateTime.toLocalDateTime();
        String day = String.valueOf(local.getDayOfMonth());
        String month = this.getMonthIndonesian(local.getMonthValue());
        String year = String.valueOf(local.getYear());
        
        return day + " " + month + " " + year;
    }

    public String getFormatSimpleTime(Timestamp param) {
        ZonedDateTime dateTime = this.getJakartaTimeZone(param);
        String[] strings = String.valueOf(dateTime).split("T");
        String[] time = strings[1].split(":");
        return time[0] + ":" + time[1];
    }

    public ZonedDateTime getJakartaTimeZone(Timestamp param) {
        Instant now = param.toInstant();
        ZoneId zone = ZoneId.of("Asia/Jakarta");
        return ZonedDateTime.ofInstant(now, zone);
    }

    public String getMonthIndonesian(Integer month) {
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
