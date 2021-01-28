package com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class NewUserRequest {
    String name;
    String email;
    String phoneNumber;
    String address;
    String province;
    String city;
    String password;
}
