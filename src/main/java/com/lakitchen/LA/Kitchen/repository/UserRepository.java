package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstById(Integer id);
    User findFirstByEmail(String email);
    User findFirstByPhoneNumber(String phoneNumber);

    @Query(value = "SELECT COUNT(id) FROM users WHERE " +
            "TO_DATE(CAST(created_at as TEXT), 'YYYY-MM-DD') <= CURRENT_DATE " +
            "AND TO_DATE(CAST(created_at as TEXT), 'YYYY-MM-DD') > (CURRENT_DATE - 7) ", nativeQuery = true)
    Integer countNewUsers();
}
