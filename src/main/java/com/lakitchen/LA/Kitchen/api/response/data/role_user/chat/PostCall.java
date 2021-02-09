package com.lakitchen.LA.Kitchen.api.response.data.role_user.chat;

import com.lakitchen.LA.Kitchen.api.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PostCall {
    Integer id;
    Integer isEnded;
    UserDTO user;
}
