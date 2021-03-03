package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.api.dto.PageableDTO;
import com.lakitchen.LA.Kitchen.api.dto.User2DTO;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.NewUserRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.ResetPasswordRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.StatusUpdateRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_admin.user.UpdateUserRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.ChangePasswordRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.RegisterRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.user.UpdateProfileRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.user.*;
import com.lakitchen.LA.Kitchen.model.entity.User;
import com.lakitchen.LA.Kitchen.model.entity.UserRole;
import com.lakitchen.LA.Kitchen.model.entity.UserStatus;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import com.lakitchen.LA.Kitchen.repository.UserRoleRepository;
import com.lakitchen.LA.Kitchen.repository.UserStatusRepository;
import com.lakitchen.LA.Kitchen.service.global.Func;
import com.lakitchen.LA.Kitchen.service.impl.UserService;
import com.lakitchen.LA.Kitchen.service.mapper.UserMapper;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.ArrayList;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserStatusRepository userStatusRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;

    @Autowired
    private Func FUNC;

    @Autowired
    private JavaMailSender javaMailSender;

    @Autowired
    private UserMapper userMapper;

    @Value("${app.frontendBaseUrl}")
    private String frontendBaseUrl;

    @Override
    public ResponseTemplate register(RegisterRequest request) {
        BasicResult result = this.validationRegister(request);

        if (result.getResult()) {
            User user = new User();

            String userMail = request.getEmail();
            String userPass = this.BCryptEncoder(request.getPassword());
            user.setUserStatus(this.unActiveStatus());
            user.setUserRole(this.roleUser());
            user.setEmail(userMail);
            user.setPassword(userPass);
            user.setPhoneNumber(request.getPhoneNumber());
            user.setCreatedAt(FUNC.getCurrentTimestamp());

            this.sendActivationMail(userMail, userPass);
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
            user.setUpdatedAt(FUNC.getCurrentTimestamp());

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
            user.setUpdatedAt(FUNC.getCurrentTimestamp());

            userRepository.save(user);

            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(),
                null, null, result.getError());
    }

    @Override
    public ResponseTemplate getAllStatus() {
        ArrayList<UserStatus> userStatus = (ArrayList<UserStatus>) userStatusRepository
                .findAll(Sort.by("id").ascending());
        ArrayList<IdNameDTO> dto = userMapper.mapToIdNameDTO(userStatus);
        return new ResponseTemplate(200, "OK", new GetAllStatus(dto), null, null);
    }

    @Override
    public ResponseTemplate resetPassword(ResetPasswordRequest request) {
        BasicResult result = this.validationResetPassword(request);

        if (result.getResult()) {
            User user = userRepository.findFirstById(request.getId());
            user.setPassword(this.BCryptEncoder("12345"));
            userRepository.save(user);
            return new ResponseTemplate(200, "OK", null, null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate statusUpdate(StatusUpdateRequest request) {
        BasicResult result = this.validationStatusUpdate(request);

        if (result.getResult()) {
            User user = userRepository.findFirstById(request.getId());
            UserStatus status = userStatusRepository.findFirstById(request.getUserStatusId());
            user.setUserStatus(status);
            userRepository.save(user);
            return new ResponseTemplate(200, "OK", null, null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getAllUserByRole(Integer page, Integer roleId) {
        Pageable paging = PageRequest.of((page-1), 10, Sort.by("name").ascending());
        Page<User> users = userRepository.findByUserRole_Id(paging, roleId);
        ArrayList<User2DTO> dto = this.helperMapToUser2DTO(users);
        PageableDTO pageableDTO = FUNC.mapToPageableDTO(users);
        return new ResponseTemplate(200, "OK", new GetAll(dto), pageableDTO, null);
    }

    @Override
    public ResponseTemplate getByStatus(Integer page, Integer statusId, Integer roleId) {
        Pageable paging = PageRequest.of((page-1), 10, Sort.by("name").ascending());
        Page<User> users = userRepository.findByUserRole_IdAndUserStatus_Id(paging, roleId, statusId);
        ArrayList<User2DTO> dto = this.helperMapToUser2DTO(users);
        PageableDTO pageableDTO = FUNC.mapToPageableDTO(users);
        return new ResponseTemplate(200, "OK", new GetByStatus(dto), pageableDTO, null);
    }

    @Override
    public ResponseTemplate getByName(Integer page, String name, Integer roleId) {
        Pageable paging = PageRequest.of((page-1), 10, Sort.by("name").ascending());
        Page<User> users = userRepository.findByUserRole_IdAndNameIgnoreCaseContaining(paging, roleId, name);
        ArrayList<User2DTO> dto = this.helperMapToUser2DTO(users);
        PageableDTO pageableDTO = FUNC.mapToPageableDTO(users);
        return new ResponseTemplate(200, "OK", new GetByName(dto), pageableDTO, null);
    }

    @Override
    public ResponseTemplate getById(Integer id) {
        User user = userRepository.findFirstById(id);
        if (user == null) {
            return new ResponseTemplate(404, "NOT FOUND", null, null, "User tidak ditemukan");
        }
        User2DTO dto = userMapper.mapToUser2DTO(user);
        return new ResponseTemplate(200, "OK", new GetById(dto), null, null);
    }

    @Override
    public ResponseTemplate createEmployee(NewUserRequest request) {
        BasicResult result = this.validationCreateEmployee(request);
        if (result.getResult()) {
            User user = new User();
            user.setUserStatus(userStatusRepository.findFirstById(1));
            user.setUserRole(userRoleRepository.findFirstById(3));
            user.setName(request.getName());
            user.setEmail(request.getEmail());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setAddress(request.getAddress());
            user.setProvince(request.getProvince());
            user.setCity(request.getCity());
            user.setPassword(this.BCryptEncoder(request.getPassword()));
            user.setCreatedAt(FUNC.getCurrentTimestamp());
            userRepository.save(user);
            return new ResponseTemplate(200, "OK", null, null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate updateEmployee(UpdateUserRequest request) {
        BasicResult result = this.validationUpdateEmployee(request);
        if (result.getResult()) {
            User user = userRepository.findFirstById(request.getId());
            user.setName(request.getName());
            user.setPhoneNumber(request.getPhoneNumber());
            user.setAddress(request.getAddress());
            user.setProvince(request.getProvince());
            user.setCity(request.getCity());
            user.setUpdatedAt(FUNC.getCurrentTimestamp());
            userRepository.save(user);
            return new ResponseTemplate(200, "OK", null, null, null);
        }
        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate activation(String userMail, String userPass) {
        BasicResult result = this.validationActivation(userMail, userPass);

        if (result.getResult()) {
            User user = userRepository.findFirstByEmail(this.decodedStr(userMail));
            user.setUserStatus(this.activeStatus());
            userRepository.save(user);
            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    private void sendActivationMail(String userMail, String userPass) {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(userMail);
        mail.setSubject("Aktivasi Akun LA' Kitchen");
        mail.setText("Silahkan klik link berikut untuk aktivasi akun LA' Kitchen : " +
                frontendBaseUrl + "/user/activation/" + this.encodedStr(userMail) +
                "/" + userPass);
        javaMailSender.send(mail);
    }

    private String baseUrl() {
        ServletUriComponentsBuilder sv = null;
        return sv.fromCurrentContextPath().build().toUriString();
    }

    private String encodedStr(String str) {
        return new String(Base64.encodeBase64(str.getBytes()));
    }

    private String decodedStr(String str) {
        return new String(Base64.decodeBase64(str.getBytes()));
    }

    private BasicResult validationActivation(String userMail, String userPass) {
        if (userMail == null || userPass == null) {
            return new BasicResult(false, null, "FORBIDDEN", 403);
        }

        String email = this.decodedStr(userMail);
        User user = userRepository.findFirstByEmailAndPassword(email, userPass);

        if (user == null) {
            return new BasicResult(false, null, "FORBIDDEN", 403);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationUpdateEmployee(UpdateUserRequest request) {
        if (request.getId() == null || request.getName() == null || request.getPhoneNumber() == null
        || request.getAddress() == null || request.getProvince() == null || request.getCity() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!FUNC.isExistUser(request.getId())) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }

        if (this.isExistPhoneNumberOther(request.getId(), request.getPhoneNumber())) {
            return new BasicResult(false, "Nomor HP telah terdaftar", "CONFLICT", 409);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationCreateEmployee(NewUserRequest request) {
        if (request.getName() == null || request.getEmail() == null || request.getPhoneNumber() == null
        || request.getAddress() == null || request.getProvince() == null || request.getCity() == null
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

    private ArrayList<User2DTO> helperMapToUser2DTO(Page<User> users) {
        ArrayList<User2DTO> dto = new ArrayList<>();
        users.getContent().forEach((val) -> {
            dto.add(userMapper.mapToUser2DTO(val));
        });
        return dto;
    }

    private BasicResult validationStatusUpdate(StatusUpdateRequest request) {
        if (request.getId() == null || request.getUserStatusId() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!FUNC.isExistUser(request.getId())) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }

        if (!this.isExistUserStatus(request.getUserStatusId())) {
            return new BasicResult(false, "Status tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationResetPassword(ResetPasswordRequest request) {
        if (request.getId() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!FUNC.isExistUser(request.getId())) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }

        return new BasicResult(true, null, "OK", 200);
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

    private UserStatus unActiveStatus() {
        return userStatusRepository.findFirstById(3);
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

    private Boolean isExistUserStatus(Integer statusId) {
        return userStatusRepository.findFirstById(statusId) != null;
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

        if (!FUNC.isExistUser(request.getId())) {
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

        if (!FUNC.isExistUser(request.getId())) {
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

