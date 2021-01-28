package com.lakitchen.LA.Kitchen.api.response.data.role_admin.user;

import com.lakitchen.LA.Kitchen.api.dto.User2DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetByStatus {
    ArrayList<User2DTO> users;
}
