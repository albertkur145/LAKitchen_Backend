package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContactDTO {
    Integer userId;
    String name;
    String email;
    Integer callId;
    Integer unreadMessages;
}
