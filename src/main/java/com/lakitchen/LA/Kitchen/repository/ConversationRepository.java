package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    ArrayList<Conversation> findByCall_IdAndCall_IsEndedOrderByCreatedAtAsc(Integer callId, Integer isEnded);
    Integer countByIsReadAndCall_IdAndFrom_Id(Integer isRead, Integer callId, Integer fromId);

    @Transactional
    @Modifying
    @Query(value = "UPDATE conversations SET is_read = 1 " +
            "WHERE call_id = ?1 AND user_from = ?2", nativeQuery = true)
    void updateIsRead(Integer callId, Integer userFrom);

}
