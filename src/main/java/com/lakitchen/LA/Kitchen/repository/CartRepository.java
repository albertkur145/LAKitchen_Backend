package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface CartRepository extends JpaRepository<Cart, Integer> {

    ArrayList<Cart> findByUser_Id(Integer userId);
    Cart findByUser_IdAndProduct_Id(Integer userId, Integer productId);
    Integer countByUser_Id(Integer userId);

    @Transactional
    void deleteByUser_IdAndProduct_Id(Integer userId, Integer productId);

}
