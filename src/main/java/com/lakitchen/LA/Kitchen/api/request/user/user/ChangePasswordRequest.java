package com.lakitchen.LA.Kitchen.api.request.user.user;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    Integer id;
    String oldPassword;
    String newPassword;
}
