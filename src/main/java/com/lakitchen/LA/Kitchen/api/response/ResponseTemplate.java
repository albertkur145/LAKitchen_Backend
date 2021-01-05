package com.lakitchen.LA.Kitchen.api.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResponseTemplate {
    public Integer code;
    public String status;
    public Object data;
    public Object paging;
    public String errors;
}
