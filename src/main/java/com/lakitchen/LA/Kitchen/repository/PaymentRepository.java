package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.api.dto.Report2DTO;
import com.lakitchen.LA.Kitchen.api.dto.Report3DTO;
import com.lakitchen.LA.Kitchen.model.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Integer> {

    Payment findFirstByOrder_OrderNumber(String orderNumber);

    @Query(value = "SELECT TO_DATE(CAST(o.finished_at as TEXT), 'YYYY-MM-DD') as createdAt, " +
            "SUM(p.total) as income FROM payments p " +
            "JOIN orders o ON (p.order_number = o.order_number) " +
            "WHERE TO_DATE(CAST(o.finished_at as TEXT), 'YYYY-MM-DD') <= CURRENT_DATE " +
            "AND TO_DATE(CAST(o.finished_at as TEXT), 'YYYY-MM-DD') > (CURRENT_DATE - 7) " +
            "GROUP BY createdAt ORDER BY createdAt ASC", nativeQuery = true)
    ArrayList<Report2DTO> findLastWeek();

    @Query(value = "SELECT TO_DATE(CAST(o.finished_at as TEXT), 'YYYY-MM-DD') as createdAt, " +
            "SUM(p.total) as income " +
            "FROM payments p JOIN orders o ON (p.order_number = o.order_number) " +
            "WHERE EXTRACT(YEAR FROM o.finished_at) = ?1 AND " +
            "EXTRACT(MONTH FROM o.finished_at) = ?2 " +
            "GROUP BY createdAt ORDER BY createdAt ASC", nativeQuery = true)
    ArrayList<Report2DTO> findMonthly(Integer year, Integer month);

    @Query(value = "SELECT EXTRACT(MONTH FROM o.finished_at) as month, " +
            "SUM(p.total) as income " +
            "FROM payments p JOIN orders o ON (p.order_number = o.order_number) " +
            "WHERE EXTRACT(YEAR FROM o.finished_at) = ?1 " +
            "GROUP BY month ORDER BY month ASC", nativeQuery = true)
    ArrayList<Report3DTO> findAnnual(Integer year);

}
