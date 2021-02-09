package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Integer> {

    ArrayList<Conversation> findByCall_IdAndCall_IsEndedOrderByCreatedAtAsc(Integer callId, Integer isEnded);

}
