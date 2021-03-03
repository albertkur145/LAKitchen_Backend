package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.UserPath;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.NewUserRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.ResetPasswordRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.StatusUpdateRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.UpdateUserRequest;
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
    @GetMapping(UserPath.USER_ACTIVATION)
    public ResponseTemplate update(@RequestParam("st") String userMail,
                                   @RequestParam("nd") String userPass) {
        return userService.activation(userMail, userPass);
    }

    // ROLE_USER
    @PutMapping(UserPath.USER_CHANGE_PASSWORD)
    public ResponseTemplate changePassword(@RequestBody ChangePasswordRequest objParam) {
        return userService.changePassword(objParam);
    }

    // ROLE_ADMIN
    @GetMapping(UserPath.ADMIN_GET_ALL_STATUS)
    public ResponseTemplate getAllStatus() {
        return userService.getAllStatus();
    }

    // ROLE_ADMIN
    @PutMapping(UserPath.ADMIN_RESET_PASSWORD)
    public ResponseTemplate resetPassword(@RequestBody ResetPasswordRequest objParam) {
        return userService.resetPassword(objParam);
    }

    // ROLE_ADMIN
    @PutMapping(UserPath.ADMIN_STATUS_UPDATE)
    public ResponseTemplate statusUpdate(@RequestBody StatusUpdateRequest objParam) {
        return userService.statusUpdate(objParam);
    }

    // ROLE_ADMIN
    @GetMapping(UserPath.ADMIN_GET_ALL_EMPLOYEE)
    public ResponseTemplate getAllEmployee(@RequestParam("page") Integer page) {
        return userService.getAllUserByRole(page, 3);
    }

    // ROLE_ADMIN
    @GetMapping(UserPath.ADMIN_GET_EMPLOYEE_BY_ID)
    public ResponseTemplate getEmployeeById(@RequestParam("id") Integer id) {
        return userService.getById(id);
    }

    // ROLE_ADMIN
    @GetMapping(UserPath.ADMIN_GET_EMPLOYEE_BY_STATUS)
    public ResponseTemplate getEmployeeByStatus(@RequestParam("page") Integer page,
                                                @RequestParam("userStatusId") Integer statusId) {
        return userService.getByStatus(page, statusId, 3);
    }

    // ROLE_ADMIN
    @GetMapping(UserPath.ADMIN_GET_EMPLOYEE_BY_NAME)
    public ResponseTemplate getEmployeeByName(@RequestParam("page") Integer page,
                                                @RequestParam("name") String name) {
        return userService.getByName(page, name, 3);
    }

    // ROLE_ADMIN
    @PostMapping(UserPath.ADMIN_POST_EMPLOYEE)
    public ResponseTemplate createEmployee(@RequestBody NewUserRequest objParam) {
        return userService.createEmployee(objParam);
    }

    // ROLE_ADMIN
    @PutMapping(UserPath.ADMIN_PUT_EMPLOYEE)
    public ResponseTemplate updateEmployee(@RequestBody UpdateUserRequest objParam) {
        return userService.updateEmployee(objParam);
    }

    // ROLE_ADMIN
    @GetMapping(UserPath.ADMIN_GET_ALL_CUSTOMER)
    public ResponseTemplate getAllCustomer(@RequestParam("page") Integer page) {
        return userService.getAllUserByRole(page, 1);
    }

    // ROLE_ADMIN
    @GetMapping(UserPath.ADMIN_GET_CUSTOMER_BY_STATUS)
    public ResponseTemplate getCustomerByStatus(@RequestParam("page") Integer page,
                                                @RequestParam("userStatusId") Integer statusId) {
        return userService.getByStatus(page, statusId, 1);
    }

    // ROLE_ADMIN
    @GetMapping(UserPath.ADMIN_GET_CUSTOMER_BY_NAME)
    public ResponseTemplate getCustomerByName(@RequestParam("page") Integer page,
                                              @RequestParam("name") String name) {
        return userService.getByName(page, name, 1);
    }

}















