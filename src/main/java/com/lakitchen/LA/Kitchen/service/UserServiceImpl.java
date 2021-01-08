package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.requestbody.user.user.ChangePasswordRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.user.user.RegisterRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.user.user.UpdateProfileRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.model.entity.User;
import com.lakitchen.LA.Kitchen.model.entity.UserRole;
import com.lakitchen.LA.Kitchen.model.entity.UserStatus;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import com.lakitchen.LA.Kitchen.repository.UserRoleRepository;
import com.lakitchen.LA.Kitchen.repository.UserStatusRepository;
import com.lakitchen.LA.Kitchen.service.impl.UserService;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Override
    public ResponseTemplate register(RegisterRequest request) {
        BasicResult result = this.validationRegister(request);

        if (result.getResult()) {
            User user = new User();

            user.setUserStatus(this.activeStatus());
            user.setUserRole(this.roleUser());
            user.setEmail(request.getEmail());
            user.setPassword(this.BCryptEncoder(request.getPassword()));
            user.setPhoneNumber(request.getPhoneNumber());
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);

            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(),
                null, null, result.getError());
    }

    @Override
    public ResponseTemplate updateProfile(UpdateProfileRequest request) {
        BasicResult result = this.validationUpdateProfile(request);

        if (result.getResult()) {
            User user = userRepository.findFirstById(request.getId());

            user.setName(request.getName());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setAddress(request.getAddress());
            user.setProvince(request.getProvince());
            user.setCity(request.getCity());
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);

            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(),
                null, null, result.getError());
    }

    @Override
    public ResponseTemplate changePassword(ChangePasswordRequest request) {
        BasicResult result = this.validationChangePassword(request);

        if (result.getResult()) {
            User user = userRepository.findFirstById(request.getId());

            user.setPassword(this.BCryptEncoder(request.getNewPassword()));
            user.setUpdatedAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);

            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(),
                null, null, result.getError());
    }

    private String BCryptEncoder(String text) {
        return new BCryptPasswordEncoder().encode(text);
    }

    private Boolean BCryptDecoder(String encodeText, String text) {
        return new BCryptPasswordEncoder().matches(text, encodeText);
    }

    private UserStatus activeStatus() {
        return userStatusRepository.findFirstById(1);
    }

    private UserRole roleUser() {
        return userRoleRepository.findFirstById(1);
    }

    private Boolean isActiveUser(Integer id) {
        User user = userRepository.findFirstById(id);
        UserStatus userStatus = userStatusRepository.findFirstById(user.getUserStatus().getId());

        return userStatus.getId().equals(1);
    }

    private Boolean isExistEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }

    private Boolean isExistPhoneNumber(String phoneNumber) {
        return userRepository.findFirstByPhoneNumber(phoneNumber) != null;
    }

    private Boolean isExistUser(Integer id) {
        return userRepository.findFirstById(id) != null;
    }

    private Boolean isExistPhoneNumberOther(Integer id, String phoneNumber) {
        User user = userRepository.findFirstByPhoneNumber(phoneNumber);

        if (user == null) {
            return false;
        }

        return !user.getId().equals(id);
    }

    private Boolean isPasswordMatch(Integer id, String passwordText) {
        User user = userRepository.findFirstById(id);

        if (this.BCryptDecoder(user.getPassword(), passwordText)) {
            return true;
        }

        return false;
    }

    private BasicResult validationRegister(RegisterRequest request) {
        if (request.getEmail() == null || request.getPhoneNumber() == null
        || request.getPassword() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (this.isExistEmail(request.getEmail())) {
            return new BasicResult(false, "Email telah terdaftar", "CONFLICT", 409);
        }

        if (this.isExistPhoneNumber(request.getPhoneNumber())) {
            return new BasicResult(false, "Nomor HP telah terdaftar", "CONFLICT", 409);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationUpdateProfile(UpdateProfileRequest request) {
        if (request.getId() == null || request.getName() == null ||
        request.getAddress() == null || request.getProvince() == null ||
        request.getCity() == null || request.getPhoneNumber() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!this.isExistUser(request.getId())) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }

        if (!this.isActiveUser(request.getId())) {
            return new BasicResult(false, "User dalam status diblokir", "NOT ACCEPTABLE", 406);
        }

        if (this.isExistPhoneNumberOther(request.getId(), request.getPhoneNumber())) {
            return new BasicResult(false, "Nomor HP telah terdaftar", "CONFLICT", 409);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationChangePassword(ChangePasswordRequest request) {
        if (request.getId() == null || request.getOldPassword() == null
        || request.getNewPassword() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!this.isExistUser(request.getId())) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }

        if (!this.isActiveUser(request.getId())) {
            return new BasicResult(false, "User dalam status diblokir", "NOT ACCEPTABLE", 406);
        }

        if (!this.isPasswordMatch(request.getId(), request.getOldPassword())) {
            return new BasicResult(false, "Password salah", "BAD REQUEST", 400);
        }

        return new BasicResult(true, null, "OK", 200);
    }

}

