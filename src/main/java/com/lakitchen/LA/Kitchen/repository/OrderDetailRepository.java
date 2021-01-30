package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.api.dto.ProductTopSellingCategoryDTO;
import com.lakitchen.LA.Kitchen.api.dto.ProductTopSellingDTO;
import com.lakitchen.LA.Kitchen.api.dto.SalesTodayDTO;
import com.lakitchen.LA.Kitchen.model.entity.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    ArrayList<OrderDetail> findByOrder_OrderNumber(String orderNumber);
    OrderDetail findFirstByOrder_OrderNumberAndProduct_Id(String orderNumber, Integer productId);
    OrderDetail findFirstByOrder_OrderNumberAndProduct_IdAndIsAssessment(String orderNumber, Integer productId, Integer isAssessment);
    ArrayList<OrderDetail> findByProduct_IdAndOrder_OrderStatus_Id(Integer productId, Integer statusId);

    @Query(value = "SELECT od.product_id as id, p.name, SUM(od.quantity) as sold " +
            "FROM order_detail od JOIN products p ON (od.product_id = p.id) " +
            "JOIN orders o ON (od.order_number = o.order_number) " +
            "WHERE o.order_status_id = 5 " +
            "GROUP BY od.product_id, p.name " +
            "ORDER BY sold DESC LIMIT ?1", nativeQuery = true)
    ArrayList<ProductTopSellingDTO> findBestSellingProduct(Integer limit);

    @Query(value = "SELECT od.product_id as id, p.name, c.name as category, " +
            "sc.name as subCategory, SUM(od.quantity) as sold " +
            "FROM order_detail od JOIN products p ON (od.product_id = p.id) " +
            "JOIN orders o ON (od.order_number = o.order_number) " +
            "JOIN sub_categories sc ON (p.sub_category_id = sc.id) " +
            "JOIN categories c ON (sc.category_id = c.id) " +
            "WHERE o.order_status_id = 5 AND c.id = ?2 " +
            "GROUP BY od.product_id, p.name, c.name, sc.name " +
            "ORDER BY sold DESC LIMIT ?1", nativeQuery = true)
    ArrayList<ProductTopSellingCategoryDTO> findBestSellingProductByCategory(Integer limit, Integer categoryId);

    @Query(value = "SELECT SUM(quantity) FROM order_detail " +
            "WHERE order_number = ?1", nativeQuery = true)
    Integer sumQuantityByOrderNumber(String orderNumber);

    @Query(value = "SELECT od.product_id as id, p.name, od.price, " +
            "SUM(od.quantity) as sold FROM order_detail od " +
            "JOIN products p ON (od.product_id = p.id) " +
            "JOIN orders o ON (od.order_number = o.order_number) " +
            "WHERE TO_DATE(CAST(o.finished_at as TEXT), 'YYYY-MM-DD') = CURRENT_DATE " +
            "GROUP BY od.product_id, p.name, od.price " +
            "ORDER BY p.name", nativeQuery = true)
    ArrayList<SalesTodayDTO> findSalesToday();
}
