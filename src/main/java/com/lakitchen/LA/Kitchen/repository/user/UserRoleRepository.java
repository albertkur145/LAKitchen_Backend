package com.lakitchen.LA.Kitchen.repository.user;

import com.lakitchen.LA.Kitchen.model.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {

    UserRole findFirstById(Integer id);
}
