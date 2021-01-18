package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.UserDTO;
import com.lakitchen.LA.Kitchen.api.requestbody.shared.LoginRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.shared.Login;
import com.lakitchen.LA.Kitchen.model.entity.User;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import com.lakitchen.LA.Kitchen.service.impl.AuthService;
import com.lakitchen.LA.Kitchen.service.mapper.UserMapper;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public ResponseTemplate login(LoginRequest request) {
        BasicResult result = this.validationLogin(request);

        if (result.getResult()) {
            User user = userRepository.findFirstByEmail(request.getEmail());
            UserDTO userDTO = userMapper.mapToUserDTO(user);
            String uuid = UUID.randomUUID().toString();

            return new ResponseTemplate(200, "OK",
                    new Login(uuid, userDTO),
                    null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    private BasicResult validationLogin(LoginRequest request) {
        User user = userRepository.findFirstByEmail(request.getEmail());

        if (user == null) {
            return new BasicResult(false, "Email tidak terdaftar", "NOT FOUND", 404);
        }

        if (user.getUserStatus().getId() != 1) {
            return new BasicResult(false, "Akun ini telah diblokir", "NOT ACCEPTABLE", 406);
        }

        if (!this.isPasswordMatch(request.getPassword(), user.getPassword())) {
            return new BasicResult(false, "Email atau password salah", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private Boolean isPasswordMatch(String request, String encodedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(request, encodedPassword);
    }
}