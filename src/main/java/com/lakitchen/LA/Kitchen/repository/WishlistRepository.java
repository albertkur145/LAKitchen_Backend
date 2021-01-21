package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.api.dto.ProductTopFavoriteCategoryDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductTopFavoriteDTO;
import com.lakitchen.LA.Kitchen.model.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {

    ArrayList<Wishlist> findByUser_Id(Integer userId);
    Wishlist findFirstByUser_IdAndProduct_Id(Integer userId, Integer productId);
    Integer countByProduct_Id(Integer productId);

    @Transactional
    void deleteByUser_IdAndProduct_Id(Integer userId, Integer productId);

    @Query(value = "SELECT p.id, p.name, COUNT(w.product_id) as popularity " +
            "FROM products p JOIN wishlists w ON (p.id = w.product_id) " +
            "GROUP BY w.product_id, p.name, p.id " +
            "ORDER BY popularity DESC LIMIT ?1", nativeQuery = true)
    ArrayList<ProductTopFavoriteDTO> findBestFavoriteProduct(Integer limit);

    @Query(value = "SELECT p.id, p.name, c.name as category, " +
            "sc.name as subCategory, COUNT(w.product_id) as popularity " +
            "FROM products p JOIN wishlists w ON (p.id = w.product_id) " +
            "JOIN sub_categories sc ON (p.sub_category_id = sc.id) " +
            "JOIN categories c ON (sc.category_id = c.id) " +
            "WHERE c.id = ?2 " +
            "GROUP BY w.product_id, p.name, p.id, c.name, sc.name " +
            "ORDER BY popularity DESC LIMIT ?1", nativeQuery = true)
    ArrayList<ProductTopFavoriteCategoryDTO> findBestFavoriteProductByCategory(Integer limit, Integer categoryId);
}
