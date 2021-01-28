package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class User2DTO {
    Integer id;
    String name;
    String email;
    String phoneNumber;
    String province;
    String city;
    String address;
    IdNameDTO status;
    String registerAt;
}
