package com.lakitchen.LA.Kitchen.repository.user;

import com.lakitchen.LA.Kitchen.model.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {

    UserStatus findFirstById(Integer id);
}
