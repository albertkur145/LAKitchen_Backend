package com.lakitchen.LA.Kitchen.api.response.data.role_user.chat;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class GetExistingCall {
    Integer callId;
    Integer isReceived;
}
