package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssessmentDTO {
    Integer id;
    IdNameDTO user;
    Integer rate;
    String comment;
    String date;
}
