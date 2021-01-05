package com.lakitchen.LA.Kitchen.api.request.user.user;

import lombok.Data;

@Data
public class UpdateProfileRequest {
    Integer id;
    String name;
    String phoneNumber;
    String address;
    String province;
    String city;
}
