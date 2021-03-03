package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.NewUserRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.ResetPasswordRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.StatusUpdateRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.UpdateUserRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.ChangePasswordRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.RegisterRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.UpdateProfileRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface UserService {

    // ROLE_USER
    ResponseTemplate register(RegisterRequest request);
    ResponseTemplate updateProfile(UpdateProfileRequest request);
    ResponseTemplate changePassword(ChangePasswordRequest request);

    // ROLE_ADMIN
    ResponseTemplate getAllStatus();
    ResponseTemplate resetPassword(ResetPasswordRequest request);
    ResponseTemplate statusUpdate(StatusUpdateRequest request);
    ResponseTemplate getAllUserByRole(Integer page, Integer roleId);
    ResponseTemplate getByStatus(Integer page, Integer statusId, Integer roleId);
    ResponseTemplate getByName(Integer page, String name, Integer roleId);
    ResponseTemplate getById(Integer id);
    ResponseTemplate createEmployee(NewUserRequest request);
    ResponseTemplate updateEmployee(UpdateUserRequest request);

    // SHARED
    ResponseTemplate activation(String userMail, String userPass);
}
