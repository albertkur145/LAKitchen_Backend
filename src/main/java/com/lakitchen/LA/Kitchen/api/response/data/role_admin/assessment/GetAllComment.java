package com.lakitchen.LA.Kitchen.api.response.data.role_admin.assessment;

import com.lakitchen.LA.Kitchen.api.dto.Assessment2DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetAllComment {
    ArrayList<Assessment2DTO> comments;
}
