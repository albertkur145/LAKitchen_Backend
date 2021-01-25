package com.lakitchen.LA.Kitchen.api.requestbody.role_admin.order;

import lombok.Data;

@Data
public class UpdateStatusRequest {
    String orderNumber;
    Integer orderStatusId;
}
