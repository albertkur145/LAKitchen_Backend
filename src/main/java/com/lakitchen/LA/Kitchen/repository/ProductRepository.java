package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.ArrayList;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Product findFirstById(Integer id);
    ArrayList<Product> findByProductSubCategory_IdAndDeletedAt(Integer subCategoryId, Timestamp deletedAt);
}
