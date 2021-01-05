package com.lakitchen.LA.Kitchen.validation;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class BasicResult {
    Boolean result;
    String error;
}
