package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.ProductAssessment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.ArrayList;

public interface ProductAssessmentRepository extends JpaRepository<ProductAssessment, Integer> {

    Integer countAllByProduct_Id(Integer productId);
    ArrayList<ProductAssessment> findByProduct_Id(Integer productId);
    ArrayList<ProductAssessment> findByProduct_IdAndDeletedAtIsNullOrderByCreatedAtDescRateDesc(Integer productId);

}
