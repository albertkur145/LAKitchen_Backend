package com.lakitchen.LA.Kitchen.api.response.data.shared;

import com.lakitchen.LA.Kitchen.api.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Login {
    String token;
    UserDTO user;
}
