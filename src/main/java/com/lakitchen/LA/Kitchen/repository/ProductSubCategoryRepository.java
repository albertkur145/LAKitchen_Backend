package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.ProductSubCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductSubCategoryRepository extends JpaRepository<ProductSubCategory, Integer> {

}
