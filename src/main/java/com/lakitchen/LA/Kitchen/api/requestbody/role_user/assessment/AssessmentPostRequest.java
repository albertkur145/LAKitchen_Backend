package com.lakitchen.LA.Kitchen.api.requestbody.role_user.assessment;

import lombok.Data;

@Data
public class AssessmentPostRequest {
    String orderNumber;
    Integer productId;
    Integer userId;
    Integer rate;
    String comment;
}
