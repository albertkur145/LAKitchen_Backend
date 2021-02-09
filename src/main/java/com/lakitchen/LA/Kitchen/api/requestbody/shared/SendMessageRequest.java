package com.lakitchen.LA.Kitchen.api.requestbody.shared;

import lombok.Data;

@Data
public class SendMessageRequest {
    Integer callId;
    Integer from;
    String message;
}
