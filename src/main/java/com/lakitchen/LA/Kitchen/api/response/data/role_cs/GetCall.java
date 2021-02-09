package com.lakitchen.LA.Kitchen.api.response.data.role_cs;

import com.lakitchen.LA.Kitchen.api.response.data.role_user.chat.PostCall;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class GetCall {
    ArrayList<PostCall> calls;
}
