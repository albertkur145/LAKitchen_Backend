package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.role_cs.ReceiveCallRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.chat.CallRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.shared.SendMessageRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface ChatService {

    // ROLE_USER
    ResponseTemplate callCS(CallRequest request);
    ResponseTemplate getCallUser(Integer callId);

    // ROLE_CS
    ResponseTemplate receiveCall(ReceiveCallRequest request);
    ResponseTemplate getAllCall();
    ResponseTemplate getContact(Integer csId);

    // ROLE_USER & ROLE_CS
    ResponseTemplate sendMessage(SendMessageRequest request);
    ResponseTemplate getAllMessage(Integer callId);
    ResponseTemplate endCall(Integer callId);

}
