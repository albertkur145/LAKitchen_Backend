package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    ArrayList<Wishlist> findByUser_Id(Integer userId);
    Wishlist findFirstByUser_IdAndProduct_Id(Integer userId, Integer productId);

    @Transactional
    void deleteByUser_IdAndProduct_Id(Integer userId, Integer productId);

}
