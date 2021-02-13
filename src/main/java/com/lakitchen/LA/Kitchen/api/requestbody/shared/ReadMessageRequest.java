package com.lakitchen.LA.Kitchen.api.requestbody.shared;

import lombok.Data;

@Data
public class ReadMessageRequest {
    Integer callId;
    Integer userFrom;
}
