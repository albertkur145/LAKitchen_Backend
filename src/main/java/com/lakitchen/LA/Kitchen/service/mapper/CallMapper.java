package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.ContactDTO;
import com.lakitchen.LA.Kitchen.api.dto.MessageDTO;
import com.lakitchen.LA.Kitchen.model.entity.Call;
import com.lakitchen.LA.Kitchen.model.entity.Conversation;
import com.lakitchen.LA.Kitchen.service.global.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CallMapper {

    @Autowired
    Func FUNC;

    public ContactDTO mapToContactDTO(Call call, Integer unreadMessages) {
        return new ContactDTO(
                call.getUser().getId(),
                call.getUser().getName(),
                call.getUser().getEmail(),
                call.getId(),
                unreadMessages
        );
    }

    public MessageDTO mapToMessageDTO(Conversation conversation) {
        return new MessageDTO(
                conversation.getId(),
                conversation.getFrom().getId(),
                conversation.getTo().getId(),
                conversation.getCall().getId(),
                conversation.getMessage(),
                conversation.getIsRead(),
                FUNC.getFormatSimpleTime(conversation.getCreatedAt())
        );
    }

}
