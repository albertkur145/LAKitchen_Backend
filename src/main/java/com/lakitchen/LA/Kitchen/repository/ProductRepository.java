package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findFirstById(Integer id);
    ArrayList<Product> findByProductSubCategory_IdAndDeletedAt(Integer subCategoryId, Timestamp deletedAt);
    ArrayList<Product> findByNameIgnoreCaseContainingAndDeletedAt(String name, Timestamp deletedAt);
    Page<Product> findByNameIgnoreCaseContaining(String name, Pageable pageable);
    ArrayList<Product> findAllByDeletedAtOrderByPriceAsc(Timestamp deletedAt);
    ArrayList<Product> findAllByDeletedAtOrderByPriceDesc(Timestamp deletedAt);

    @Query(value = "SELECT * FROM products p " +
            "JOIN sub_categories s ON (p.sub_category_id = s.id) " +
            "JOIN categories cg ON (s.category_id = cg.id) " +
            "WHERE cg.id = ?1 AND p.deleted_at IS null",
            nativeQuery = true)
    ArrayList<Product> findByCategory(Integer categoryId);

    @Query(value = "SELECT * FROM products p " +
            "JOIN sub_categories s ON (p.sub_category_id = s.id) " +
            "JOIN categories cg ON (s.category_id = cg.id) " +
            "WHERE cg.id = ?1",
            nativeQuery = true)
    Page<Product> findByCategoryAdmin(Integer categoryId, Pageable pageable);
}
