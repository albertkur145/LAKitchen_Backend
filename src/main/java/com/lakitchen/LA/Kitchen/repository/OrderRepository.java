package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    Order findFirstByOrderNumber(String orderNumber);
    Order findFirstByOrderNumberAndOrderStatus_Id(String orderNumber, Integer id);
    Order findFirstByOrderNumberAndOrderStatus_IdAndUser_Id(String orderNumber, Integer id, Integer userId);
    ArrayList<Order> findByUser_IdOrderByCreatedAtDesc(Integer userId);
    Page<Order> findByOrderStatus_Id(Pageable pageable, Integer statusId);
    Integer countByOrderStatus_Id(Integer statusId);

    @Query(value = "SELECT COUNT(created_at) FROM orders WHERE " +
            "TO_DATE(CAST(created_at as TEXT), 'YYYY-MM-DD') = CURRENT_DATE",
            nativeQuery = true)
    Integer countOrderToday();

    @Query(value = "SELECT * FROM orders WHERE " +
            "TO_DATE(CAST(created_at as TEXT), 'YYYY-MM-DD') = CURRENT_DATE " +
            "AND order_status_id = ?1",
            nativeQuery = true)
    ArrayList<Order> findByCurrentDate(Integer statusId);

    @Query(value = "SELECT * FROM orders WHERE " +
            "TO_DATE(CAST(created_at as TEXT), 'YYYY-MM-DD') <= CURRENT_DATE " +
            "AND TO_DATE(CAST(created_at as TEXT), 'YYYY-MM-DD') > (CURRENT_DATE - 7) " +
            "AND order_status_id = ?1",
            nativeQuery = true)
    ArrayList<Order> findByLastWeek(Integer statusId);

}
