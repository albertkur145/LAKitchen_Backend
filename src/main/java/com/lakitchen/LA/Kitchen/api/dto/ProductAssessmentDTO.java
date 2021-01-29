package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class ProductAssessmentDTO {
    Integer id;
    IdNameDTO user;
    Integer rate;
    String comment;
    String date;
    Timestamp deletedAt;
}
