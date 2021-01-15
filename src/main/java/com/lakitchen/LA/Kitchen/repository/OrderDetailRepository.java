package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    ArrayList<OrderDetail> findByOrder_OrderNumber(String orderNumber);

}
