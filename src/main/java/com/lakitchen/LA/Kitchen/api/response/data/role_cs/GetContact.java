package com.lakitchen.LA.Kitchen.api.response.data.role_cs;

import com.lakitchen.LA.Kitchen.api.dto.ContactDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetContact {
    ArrayList<ContactDTO> contacts;
}
