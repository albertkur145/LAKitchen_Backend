package com.lakitchen.LA.Kitchen.api.response.data.shared;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetCategories {
    public ArrayList<IdNameDTO> categories;
}
