package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UserDTO {
    Integer id;
    String name;
    String email;
    String phoneNumber;
    String address;
    String province;
    String city;
    String registerAt;
    String status;
    String role;
}
