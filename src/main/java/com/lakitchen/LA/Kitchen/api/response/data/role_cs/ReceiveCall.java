package com.lakitchen.LA.Kitchen.api.response.data.role_cs;

import com.lakitchen.LA.Kitchen.api.dto.ContactDTO;
import com.lakitchen.LA.Kitchen.api.dto.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReceiveCall {
    ContactDTO contact;
}
