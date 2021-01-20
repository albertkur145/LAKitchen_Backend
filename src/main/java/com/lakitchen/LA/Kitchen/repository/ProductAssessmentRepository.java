package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.ProductAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ProductAssessmentRepository extends JpaRepository<ProductAssessment, Integer> {

    Integer countAllByProduct_Id(Integer productId);
    ArrayList<ProductAssessment> findByProduct_Id(Integer productId);
    ArrayList<ProductAssessment> findByProduct_IdOrderByCreatedAtDescRateDesc(Integer productId);

    @Query(value = "SELECT * FROM product_assessments pa " +
            "JOIN products p ON (pa.product_id = p.id) " +
            "WHERE pa.product_id = ?1 ORDER BY pa.created_at desc, pa.rate desc " +
            "LIMIT ?2", nativeQuery = true)
    ArrayList<ProductAssessment> findByProduct_IdLimit(Integer productId, Integer limit);
}
