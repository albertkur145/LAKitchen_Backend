package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    User findFirstById(Integer id);
    User findFirstByEmail(String email);
    User findFirstByPhoneNumber(String phoneNumber);
    User findFirstByEmailAndUserStatus_Id(String email, Integer statusId);
}
