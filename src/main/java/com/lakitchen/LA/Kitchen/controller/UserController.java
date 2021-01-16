package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.UserPath;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.ChangePasswordRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.RegisterRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.UpdateProfileRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    // ROLE_USER
    @PostMapping(UserPath.USER_POST)
    public ResponseTemplate register(@RequestBody RegisterRequest objParam) {
        return userService.register(objParam);
    }

    // ROLE_USER
    @PutMapping(UserPath.USER_PUT)
    public ResponseTemplate update(@RequestBody UpdateProfileRequest objParam) {
        return userService.updateProfile(objParam);
    }

    // ROLE_USER
    @PutMapping(UserPath.USER_CHANGE_PASSWORD)
    public ResponseTemplate changePassword(@RequestBody ChangePasswordRequest objParam) {
        return userService.changePassword(objParam);
    }
}















