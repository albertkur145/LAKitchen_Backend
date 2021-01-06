package com.lakitchen.LA.Kitchen.controller.user;

import com.lakitchen.LA.Kitchen.api.path.user.UserPath;
import com.lakitchen.LA.Kitchen.api.requestbody.user.user.ChangePasswordRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.user.user.RegisterRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.user.user.UpdateProfileRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.user.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    private UserServiceImpl userService;

    @PostMapping(UserPath.USER_POST)
    public ResponseTemplate register(@RequestBody RegisterRequest objParam) {
        return userService.register(objParam);
    }

    @PutMapping(UserPath.USER_PUT)
    public ResponseTemplate update(@RequestBody UpdateProfileRequest objParam) {
        return userService.updateProfile(objParam);
    }

    @PutMapping(UserPath.USER_CHANGE_PASSWORD)
    public ResponseTemplate changePassword(@RequestBody ChangePasswordRequest objParam) {
        return userService.changePassword(objParam);
    }
}















