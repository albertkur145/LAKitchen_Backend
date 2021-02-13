package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.api.dto.MessageDTO;
import com.lakitchen.LA.Kitchen.api.path.ChatPath;
import com.lakitchen.LA.Kitchen.api.requestbody.role_cs.ReceiveCallRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.chat.CallRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.shared.ReadMessageRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.shared.SendMessageRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_cs.ReceiveCall;
import com.lakitchen.LA.Kitchen.service.ChatServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class ChatController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    ChatServiceImpl chatService;

    // ROLE_USER
    @PostMapping(ChatPath.CHAT_CALL)
    public ResponseTemplate callCS(@RequestBody CallRequest objParam) {
        ResponseTemplate res = chatService.callCS(objParam);
        if (res.getCode() != 200) {
            return null;
        }
        this.call(res.data);
        return res;
    }

    // ROLE_USER
    @GetMapping(ChatPath.CHAT_GET_ALL_MESSAGE)
    public ResponseTemplate getAllMessage(@RequestParam("callId") Integer callId) {
        return chatService.getAllMessage(callId);
    }

    // ROLE_USER
    @GetMapping(ChatPath.CHAT_GET_CALL)
    public ResponseTemplate getCallUser(@RequestParam("callId") Integer callId) {
        return chatService.getCallUser(callId);
    }

    // ROLE_USER
    private void announceReceived(Integer callId, Object payload) {
        simpMessagingTemplate.convertAndSend("/call/accepted/" + callId, payload);
    }

    // ROLE_CS
    private void call(Object payload) {
        simpMessagingTemplate.convertAndSend("/call", payload);
    }

    // ROLE_CS
    private void receivedCall(Object payload) {
        simpMessagingTemplate.convertAndSend("/call/received", payload);
    }

    // ROLE_CS
    @PostMapping(ChatPath.CS_CALL_RECEIVE)
    public ResponseTemplate receiveCall(@RequestBody ReceiveCallRequest objParam) {
        ResponseTemplate res = chatService.receiveCall(objParam);
        if (res.getCode() != 200) {
            return null;
        }
        ReceiveCall payload = (ReceiveCall) res.getData();
        this.receivedCall(this.getAllCall().getData());
        this.announceReceived(payload.getContact().getCallId(), payload);
        return res;
    }

    // ROLE_CS
    @GetMapping(ChatPath.CS_GET_ALL_CALL)
    public ResponseTemplate getAllCall() {
        return chatService.getAllCall();
    }

    // ROLE_CS
    @GetMapping(ChatPath.CS_GET_CONTACT)
    public ResponseTemplate getContact(@RequestParam("csId") Integer csId) {
        return chatService.getContact(csId);
    }

    // ROLE_CS
    @GetMapping(ChatPath.CS_GET_ALL_MESSAGE)
    public ResponseTemplate csGetMessage(@RequestParam("callId") Integer callId) {
        return chatService.getAllMessage(callId);
    }

    // ROLE_CS & ROLE_USER
    @PostMapping(ChatPath.CHAT_SEND_MESSAGE)
    public ResponseTemplate sendMessage(@RequestBody SendMessageRequest objParam) {
        ResponseTemplate res = chatService.sendMessage(objParam);
        if (res.getCode() != 200) {
            return res;
        }
        MessageDTO data = (MessageDTO) res.getData();
        this.messagesCh(data.getCallId(), data);
        return res;
    }

    // ROLE_CS & ROLE_USER
    @DeleteMapping(ChatPath.CHAT_END_CALL)
    public ResponseTemplate endCall(@RequestParam("callId") Integer callId) {
        ResponseTemplate res = chatService.endCall(callId);
        if (res.getCode() != 200) {
            return res;
        }
        IdNameDTO data = new IdNameDTO(callId, null);
        this.endedCallCh(callId, data);
        return res;
    }

    // ROLE_CS & ROLE_USER
    @PostMapping(ChatPath.CHAT_READ_MESSAGE)
    public ResponseTemplate readMessage(@RequestBody ReadMessageRequest objParam) {
        return chatService.readMessage(objParam);
    }

    // ROLE_CS & ROLE_USER
    private void messagesCh(Integer callId, Object payload) {
        simpMessagingTemplate.convertAndSend("/message/" + callId, payload);
    }

    // ROLE_CS & ROLE_USER
    private void endedCallCh(Integer callId, Object payload) {
        simpMessagingTemplate.convertAndSend("/call/ended/" + callId, payload);
    }

}
