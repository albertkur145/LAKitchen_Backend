package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {

    OrderStatus findFirstById(Integer id);
    ArrayList<OrderStatus> findByIdIn(int[] id);

}
