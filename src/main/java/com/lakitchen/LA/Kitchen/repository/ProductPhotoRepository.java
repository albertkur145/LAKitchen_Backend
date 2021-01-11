package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.ProductPhoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ProductPhotoRepository extends JpaRepository<ProductPhoto, Integer> {

    ProductPhoto findFirstByProduct_Id(Integer productId);
    ArrayList<ProductPhoto> findByProduct_Id(Integer productId);

}
