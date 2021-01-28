package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.api.dto.User2DTO;
import com.lakitchen.LA.Kitchen.api.dto.UserDTO;
import com.lakitchen.LA.Kitchen.model.entity.User;
import com.lakitchen.LA.Kitchen.model.entity.UserStatus;
import com.lakitchen.LA.Kitchen.service.global.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class UserMapper {

    @Autowired
    Func FUNC;

    public UserDTO mapToUserDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAddress(),
                user.getProvince(),
                user.getCity(),
                FUNC.getFormatDateSimplified(user.getCreatedAt()),
                user.getUserStatus().getName(),
                user.getUserRole().getName()
        );
    }

    public User2DTO mapToUser2DTO(User user) {
        return new User2DTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getProvince(),
                user.getCity(),
                user.getAddress(),
                new IdNameDTO(user.getUserStatus().getId(), user.getUserStatus().getName()),
                FUNC.getFormatDateSlash(user.getCreatedAt())
        );
    }

    public ArrayList<IdNameDTO> mapToIdNameDTO(ArrayList<UserStatus> userStatus) {
        ArrayList<IdNameDTO> dto = new ArrayList<>();
        userStatus.forEach((val) -> {
            dto.add(FUNC.mapToIdNameDTO(val.getId(), val.getName()));
        });
        return dto;
    }

}
