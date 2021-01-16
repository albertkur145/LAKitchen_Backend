package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findFirstByOrderNumber(String orderNumber);
    Order findFirstByOrderNumberAndOrderStatus_Id(String orderNumber, Integer id);
    Order findFirstByOrderNumberAndOrderStatus_IdAndUser_Id(String orderNumber, Integer id, Integer userId);
    ArrayList<Order> findByUser_Id(Integer userId);

}
