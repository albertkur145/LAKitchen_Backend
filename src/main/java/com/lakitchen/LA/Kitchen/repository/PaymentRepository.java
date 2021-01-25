package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findFirstByOrder_OrderNumber(String orderNumber);

    @Query(value = "SELECT * FROM payments p " +
            "JOIN orders o ON (p.order_number = o.order_number) WHERE " +
            "TO_DATE(CAST(p.created_at as TEXT), 'YYYY-MM-DD') <= CURRENT_DATE AND " +
            "TO_DATE(CAST(p.created_at as TEXT), 'YYYY-MM-DD') > (CURRENT_DATE - 7) " +
            "AND o.order_status_id = ?1 ORDER BY p.created_at ASC", nativeQuery = true)
    ArrayList<Payment> findLastWeek(Integer statusId);

}
