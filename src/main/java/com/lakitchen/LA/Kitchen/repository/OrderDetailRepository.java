package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    ArrayList<OrderDetail> findByOrder_OrderNumber(String orderNumber);
    OrderDetail findFirstByOrder_OrderNumberAndProduct_Id(String orderNumber, Integer productId);
    OrderDetail findFirstByOrder_OrderNumberAndProduct_IdAndIsAssessment(String orderNumber, Integer productId, Integer isAssessment);
    ArrayList<OrderDetail> findByProduct_IdAndOrder_OrderStatus_Id(Integer productId, Integer statusId);

}
