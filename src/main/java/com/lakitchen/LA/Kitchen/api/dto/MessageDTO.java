package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MessageDTO {
    Integer id;
    Integer from;
    Integer to;
    Integer callId;
    String message;
    Integer isRead;
    String time;
}
