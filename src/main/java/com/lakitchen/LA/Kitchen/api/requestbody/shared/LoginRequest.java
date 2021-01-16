package com.lakitchen.LA.Kitchen.api.requestbody.shared;

import lombok.Data;

@Data
public class LoginRequest {
    String email;
    String password;
}
