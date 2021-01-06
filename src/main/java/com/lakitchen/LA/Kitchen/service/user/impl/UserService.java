package com.lakitchen.LA.Kitchen.service.user.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.user.user.ChangePasswordRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.user.user.RegisterRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.user.user.UpdateProfileRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface UserService {
    ResponseTemplate register(RegisterRequest request);
    ResponseTemplate updateProfile(UpdateProfileRequest request);
    ResponseTemplate changePassword(ChangePasswordRequest request);
}
