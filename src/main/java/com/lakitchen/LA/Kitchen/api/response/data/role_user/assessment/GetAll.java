package com.lakitchen.LA.Kitchen.api.response.data.role_user.assessment;

import com.lakitchen.LA.Kitchen.api.dto.AssessmentDTO;
import com.lakitchen.LA.Kitchen.api.dto.PathDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetAll {
    ArrayList<AssessmentDTO> assessment;
}
