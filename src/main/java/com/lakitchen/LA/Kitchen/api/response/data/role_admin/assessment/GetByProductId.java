package com.lakitchen.LA.Kitchen.api.response.data.role_admin.assessment;

import com.lakitchen.LA.Kitchen.api.dto.RateDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
public class GetByProductId {
    String product;
    RateDTO rate;
    Integer totalAssessment;
    Integer evaluators;
    Double rating;
    Integer totalComments;
}
