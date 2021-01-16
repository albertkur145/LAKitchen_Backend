package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.ChangePasswordRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.RegisterRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.UpdateProfileRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface UserService {

    // ROLE_USER
    ResponseTemplate register(RegisterRequest request);
    ResponseTemplate updateProfile(UpdateProfileRequest request);
    ResponseTemplate changePassword(ChangePasswordRequest request);

}
