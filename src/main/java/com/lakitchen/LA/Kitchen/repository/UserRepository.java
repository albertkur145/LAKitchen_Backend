package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstById(Integer id);
    User findFirstByEmail(String email);
    User findFirstByPhoneNumber(String phoneNumber);
    User findFirstByEmailAndPassword(String email, String password);
    Page<User> findByUserRole_Id(Pageable pageable, Integer roleId);
    Page<User> findByUserRole_IdAndUserStatus_Id(Pageable pageable, Integer roleId, Integer statusId);
    Page<User> findByUserRole_IdAndNameIgnoreCaseContaining(Pageable pageable, Integer roleId, String name);

    @Query(value = "SELECT COUNT(id) FROM users WHERE " +
            "TO_DATE(CAST(created_at as TEXT), 'YYYY-MM-DD') <= CURRENT_DATE " +
            "AND TO_DATE(CAST(created_at as TEXT), 'YYYY-MM-DD') > (CURRENT_DATE - 7) " +
            "AND role_id = 1", nativeQuery = true)
    Integer countNewUsers();

    @Query(value = "SELECT COUNT(id) FROM users WHERE role_id = 1", nativeQuery = true)
    Integer countAllUser();

    @Transactional
    void deleteById(Integer userId);
}
