package com.lakitchen.LA.Kitchen.service.user.impl;

import com.lakitchen.LA.Kitchen.api.request.user.user.PostRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface UserService {
    ResponseTemplate register(PostRequest postRequest);
}
