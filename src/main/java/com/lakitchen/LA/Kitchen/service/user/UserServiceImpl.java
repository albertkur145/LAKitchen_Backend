package com.lakitchen.LA.Kitchen.service.user;

import com.lakitchen.LA.Kitchen.api.request.user.user.PostRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.model.entity.User;
import com.lakitchen.LA.Kitchen.model.entity.UserRole;
import com.lakitchen.LA.Kitchen.model.entity.UserStatus;
import com.lakitchen.LA.Kitchen.repository.user.UserRepository;
import com.lakitchen.LA.Kitchen.repository.user.UserRoleRepository;
import com.lakitchen.LA.Kitchen.repository.user.UserStatusRepository;
import com.lakitchen.LA.Kitchen.service.user.impl.UserService;
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

    private String BCryptEncoder(String text) {
        return new BCryptPasswordEncoder().encode(text);
    }

    private UserStatus activeStatus() {
        return userStatusRepository.findFirstById(1);
    }

    private UserRole roleUser() {
        return userRoleRepository.findFirstById(1);
    }

    private Boolean isExistEmail(String email) {
        return userRepository.findFirstByEmail(email) != null;
    }

    private Boolean isExistPhoneNumber(String phoneNumber) {
        return userRepository.findFirstByPhoneNumber(phoneNumber) != null;
    }

    private BasicResult validationRegister(PostRequest postRequest) {
        if (postRequest.getEmail() == null || postRequest.getPhoneNumber() == null
        || postRequest.getPassword() == null) {
            return new BasicResult(false, "Form tidak lengkap");
        }

        if (this.isExistEmail(postRequest.getEmail())) {
            return new BasicResult(false, "Email telah terdaftar");
        }

        if (this.isExistPhoneNumber(postRequest.getPhoneNumber())) {
            return new BasicResult(false, "Nomor HP telah terdaftar");
        }

        return new BasicResult(true, null);
    }

    @Override
    public ResponseTemplate register(PostRequest postRequest) {
        BasicResult basicResult = this.validationRegister(postRequest);

        if (basicResult.getResult()) {
            User user = new User();

            user.setUserStatus(this.activeStatus());
            user.setUserRole(this.roleUser());
            user.setEmail(postRequest.getEmail());
            user.setPassword(this.BCryptEncoder(postRequest.getPassword()));
            user.setPhoneNumber(postRequest.getPhoneNumber());
            user.setCreatedAt(new Timestamp(System.currentTimeMillis()));

            userRepository.save(user);

            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(
                400,
                "BAD REQUEST",
                null,
                null,
                basicResult.getError());
    }
}

