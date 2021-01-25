package com.lakitchen.LA.Kitchen.api.response.data.role_admin.order.status;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetFinishedStatus {
    ArrayList<IdNameDTO> status;
}
