package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Integer> {

    ProductPhoto findFirstByProduct_Id(Integer productId);
    ProductPhoto findFirstById(Integer id);
    ArrayList<ProductPhoto> findByProduct_Id(Integer productId);

    @Transactional
    void deleteById(Integer id);

    @Transactional
    void deleteByProduct_Id(Integer productId);
}
