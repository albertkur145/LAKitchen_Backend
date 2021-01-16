package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.shared.LoginRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface AuthService {

    public ResponseTemplate login(LoginRequest request);

}
