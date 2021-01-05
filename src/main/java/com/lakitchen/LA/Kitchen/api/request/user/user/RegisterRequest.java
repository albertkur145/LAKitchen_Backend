package com.lakitchen.LA.Kitchen.api.request.user.user;

import lombok.Data;

@Data
public class RegisterRequest {
    String email;
    String password;
    String phoneNumber;
}
