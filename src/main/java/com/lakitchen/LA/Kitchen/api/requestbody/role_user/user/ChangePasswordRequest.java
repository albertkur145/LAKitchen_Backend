package com.lakitchen.LA.Kitchen.api.requestbody.role_user.user;

import lombok.Data;

@Data
public class ChangePasswordRequest {
    Integer id;
    String oldPassword;
    String newPassword;
}
