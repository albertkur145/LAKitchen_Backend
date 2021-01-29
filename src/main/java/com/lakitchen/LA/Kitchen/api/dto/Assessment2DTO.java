package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class Assessment2DTO {
    Integer id;
    String username;
    Integer rate;
    String comment;
    String date;
    Timestamp deletedAt;
}
