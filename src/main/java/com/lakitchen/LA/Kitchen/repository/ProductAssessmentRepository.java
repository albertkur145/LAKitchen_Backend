package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.api.dto.DetailAssessmentDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductTopRatingCategoryDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductTopRatingDTO;
import com.lakitchen.LA.Kitchen.model.entity.ProductAssessment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

public interface ProductAssessmentRepository extends JpaRepository<ProductAssessment, Integer> {

    Integer countAllByProduct_Id(Integer productId);
    ArrayList<ProductAssessment> findByProduct_Id(Integer productId);
    ArrayList<ProductAssessment> findByProduct_IdOrderByCreatedAtDescRateDesc(Integer productId);
    ArrayList<ProductAssessment> findByProduct_IdOrderByCreatedAtDesc(Integer productId);
    Page<ProductAssessment> findByProduct_Id(Pageable pageable, Integer productId);
    ProductAssessment findFirstById(Integer id);

    @Query(value = "SELECT * FROM product_assessments pa " +
            "JOIN products p ON (pa.product_id = p.id) " +
            "WHERE pa.product_id = ?1 ORDER BY pa.created_at desc, pa.rate desc " +
            "LIMIT ?2", nativeQuery = true)
    ArrayList<ProductAssessment> findByProduct_IdLimit(Integer productId, Integer limit);

    @Query(value = "SELECT pa.product_id as id, p.name, " +
            "ROUND(CAST(AVG(pa.rate) as numeric), 2) as rating " +
            "FROM product_assessments pa JOIN products p ON (pa.product_id = p.id) " +
            "GROUP BY pa.product_id, p.name ORDER BY rating DESC " +
            "LIMIT ?1", nativeQuery = true)
    ArrayList<ProductTopRatingDTO> findTopRatingProduct(Integer limit);

    @Query(value = "SELECT pa.product_id as id, p.name, " +
            "c.name as category, sc.name as subCategory, " +
            "COUNT(pa.product_id) as evaluators, " +
            "SUM(case when pa.comment is null then 0 else 1 end) as comments, " +
            "ROUND(CAST(AVG(pa.rate) as numeric), 2) as rating " +
            "FROM product_assessments pa JOIN products p ON (pa.product_id = p.id) " +
            "JOIN sub_categories sc ON (p.sub_category_id = sc.id) " +
            "JOIN categories c ON (sc.category_id = c.id) " +
            "WHERE c.id = ?2 " +
            "GROUP BY pa.product_id, p.name, c.name, sc.name " +
            "ORDER BY rating DESC LIMIT ?1", nativeQuery = true)
    ArrayList<ProductTopRatingCategoryDTO> findTopRatingProductByCategory(Integer limit, Integer categoryId);

    @Query(value = "SELECT SUM(rate) as totalAssessments, " +
            "COUNT(user_id) as evaluators, " +
            "ROUND(CAST(AVG(rate) as numeric), 2) as rating, " +
            "COUNT(CASE WHEN comment IS NOT NULL AND deleted_at IS NULL THEN 1 END) as totalComments " +
            "FROM product_assessments WHERE product_id = ?1", nativeQuery = true)
    DetailAssessmentDTO findDetailAssessmentByProductId(Integer productId);

    @Query(value = "SELECT COUNT(rate) FROM product_assessments " +
            "WHERE product_id = ?1 AND rate = ?2", nativeQuery = true)
    Integer countSpesificRate(Integer productId, Integer rate);

}
