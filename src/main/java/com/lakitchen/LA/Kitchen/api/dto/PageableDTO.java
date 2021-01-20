package com.lakitchen.LA.Kitchen.api.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PageableDTO {
    Integer page;
    Integer count;
    Integer view;
}
