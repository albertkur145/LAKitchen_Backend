package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.model.entity.OrderStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrderStatusMapper {

    public IdNameDTO mapToIdNameDTO(Integer id, String name) {
        return new IdNameDTO(id, name);
    }

    public ArrayList<IdNameDTO> helperMapOrderStatus(ArrayList<OrderStatus> orderStatus) {
        ArrayList<IdNameDTO> dto = new ArrayList<>();
        orderStatus.forEach((val) -> {
            dto.add(this.mapToIdNameDTO(val.getId(), val.getName()));
        });
        return dto;
    }

}
