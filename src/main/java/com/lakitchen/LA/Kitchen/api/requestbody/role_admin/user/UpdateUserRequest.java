package com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user;

import lombok.Data;

@Data
public class UpdateUserRequest {
    Integer id;
    String name;
    String phoneNumber;
    String address;
    String province;
    String city;
}
