package com.lakitchen.LA.Kitchen.api.response.data.role_admin.user;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetAllStatus {
    ArrayList<IdNameDTO> status;
}
