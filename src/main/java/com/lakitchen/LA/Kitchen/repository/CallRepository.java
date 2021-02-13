package com.lakitchen.LA.Kitchen.repository;

import com.lakitchen.LA.Kitchen.model.entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CallRepository extends JpaRepository<Call, Integer> {

    Call findFirstById(Integer id);
    Call findFirstByIdAndIsEndedAndCsIsNotNull(Integer id, Integer isEnded);
    Call findFirstByIdAndIsEnded(Integer id, Integer isEnded);
    ArrayList<Call> findByReceivedAtIsNullOrderByCreatedAtAsc();
    ArrayList<Call> findByCs_IdAndIsEndedOrderByCreatedAtDesc(Integer csId, Integer isEnded);

}
