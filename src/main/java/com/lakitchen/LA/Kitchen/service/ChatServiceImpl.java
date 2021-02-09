package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.ContactDTO;
import com.lakitchen.LA.Kitchen.api.dto.MessageDTO;
import com.lakitchen.LA.Kitchen.api.requestbody.role_cs.ReceiveCallRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.chat.CallRequest;
import com.lakitchen.LA.Kitchen.api.requestbody.shared.SendMessageRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_cs.GetCall;
import com.lakitchen.LA.Kitchen.api.response.data.role_cs.GetContact;
import com.lakitchen.LA.Kitchen.api.response.data.role_cs.ReceiveCall;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.chat.GetExistingCall;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.chat.PostCall;
import com.lakitchen.LA.Kitchen.api.response.data.shared.GetMessages;
import com.lakitchen.LA.Kitchen.model.entity.Call;
import com.lakitchen.LA.Kitchen.model.entity.Conversation;
import com.lakitchen.LA.Kitchen.model.entity.User;
import com.lakitchen.LA.Kitchen.repository.CallRepository;
import com.lakitchen.LA.Kitchen.repository.ConversationRepository;
import com.lakitchen.LA.Kitchen.repository.UserRepository;
import com.lakitchen.LA.Kitchen.service.global.Func;
import com.lakitchen.LA.Kitchen.service.impl.ChatService;
import com.lakitchen.LA.Kitchen.service.mapper.CallMapper;
import com.lakitchen.LA.Kitchen.service.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;

@Service
public class ChatServiceImpl implements ChatService {

    @Autowired
    CallRepository callRepository;

    @Autowired
    ConversationRepository conversationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Autowired
    CallMapper callMapper;

    @Autowired
    Func FUNC;

    @Override
    public ResponseTemplate callCS(CallRequest request) {
        Call call = new Call();
        call.setCreatedAt(FUNC.getCurrentTimestamp());
        call.setUser(userRepository.findFirstById(request.getUserId()));
        call.setIsEnded(0);
        Call saved = callRepository.save(call);
        PostCall response = new PostCall(saved.getId(),
                saved.getIsEnded(), userMapper.mapToUserDTO(saved.getUser()));
        return new ResponseTemplate(200, "OK", response, null, null);
    }

    @Override
    public ResponseTemplate getCallUser(Integer callId) {
        Call call = callRepository.findFirstByIdAndIsEnded(callId, 0);
        if (call == null) {
            return new ResponseTemplate(404, "NOT FOUND",
                    null, null, "Call tidak ditemukan");
        }

        int isReceived = 1;
        if (call.getCs() == null) {
            isReceived = 0;
        }
        return new ResponseTemplate(200, "OK",
                new GetExistingCall(call.getId(), isReceived), null, null);
    }

    @Override
    public ResponseTemplate getAllMessage(Integer callId) {
        ArrayList<Conversation> data = conversationRepository
                .findByCall_IdAndCall_IsEndedOrderByCreatedAtAsc(callId, 0);
        ArrayList<MessageDTO> dto = this.helperMapToMessageDTO(data);
        return new ResponseTemplate(200, "OK",
                new GetMessages(dto), null, null);
    }

    @Override
    public ResponseTemplate endCall(Integer callId) {
        Call call = callRepository.findFirstById(callId);
        if (call == null) {
            return new ResponseTemplate(404, "NOT FOUND",
                    null, null, "Call tidak ditemukan");
        }

        call.setIsEnded(1);
        callRepository.save(call);
        return new ResponseTemplate(200, "OK",
                null, null, null);
    }

    @Override
    public ResponseTemplate receiveCall(ReceiveCallRequest request) {
        Call call = callRepository.findFirstById(request.getId());
        call.setCs(userRepository.findFirstById(request.getCsId()));
        call.setReceivedAt(FUNC.getCurrentTimestamp());
        call.setIsEnded(0);
        Call saved = callRepository.save(call);
        ContactDTO dto = callMapper.mapToContactDTO(saved, null);
        ReceiveCall response = new ReceiveCall(dto);
        return new ResponseTemplate(200, "OK", response, null, null);
    }

    @Override
    public ResponseTemplate getAllCall() {
        ArrayList<Call> calls = callRepository.findByReceivedAtIsNullOrderByCreatedAtAsc();
        ArrayList<PostCall> response = this.helperMapToPostCall(calls);
        return new ResponseTemplate(200, "OK",
                new GetCall(response), null, null);
    }

    @Override
    public ResponseTemplate getContact(Integer csId) {
        ArrayList<Call> calls = callRepository.findByCs_IdAndIsEnded(csId, 0);
        ArrayList<ContactDTO> dto = this.helperMapToContactDTO(calls);
        return new ResponseTemplate(200, "OK",
                new GetContact(dto), null, null);
    }

    @Override
    public ResponseTemplate sendMessage(SendMessageRequest request) {
        Call call = callRepository
                .findFirstByIdAndIsEndedAndCsIsNotNull(request.getCallId(), 0);

        if (call == null) {
            return new ResponseTemplate(404, "NOT FOUND",
                    null, null, "Call tidak ditemukan");
        }

        User to = null;
        if (request.getFrom().equals(call.getCs().getId())) {
            to = call.getUser();
        } else {
            to = call.getCs();
        }

        Conversation data = new Conversation();
        data.setCreatedAt(FUNC.getCurrentTimestamp());
        data.setMessage(request.getMessage());
        data.setCall(call);
        data.setFrom(userRepository.findFirstById(request.getFrom()));
        data.setTo(to);
        data.setIsRead(0);
        Conversation saved = conversationRepository.save(data);
        MessageDTO dto = callMapper.mapToMessageDTO(saved);
        return new ResponseTemplate(200, "OK", dto, null, null);
    }

    private ArrayList<MessageDTO> helperMapToMessageDTO(ArrayList<Conversation> conversations) {
        ArrayList<MessageDTO> dto = new ArrayList<>();
        conversations.forEach((val) -> {
            dto.add(callMapper.mapToMessageDTO(val));
        });
        return dto;
    }

    private ArrayList<ContactDTO> helperMapToContactDTO(ArrayList<Call> calls) {
        ArrayList<ContactDTO> dto = new ArrayList<>();
        calls.forEach((val) -> {
            dto.add(callMapper.mapToContactDTO(val, 0));
        });
        return dto;
    }

    private ArrayList<PostCall> helperMapToPostCall(ArrayList<Call> calls) {
        ArrayList<PostCall> dto = new ArrayList<>();
        calls.forEach((val) -> {
            dto.add(new PostCall(val.getId(), val.getIsEnded(),
                    userMapper.mapToUserDTO(val.getUser())));
        });
        return dto;
    }

}
