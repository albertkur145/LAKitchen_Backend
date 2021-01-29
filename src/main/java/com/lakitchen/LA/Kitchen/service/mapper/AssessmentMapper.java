package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.Assessment2DTO;
import com.lakitchen.LA.Kitchen.api.dto.AssessmentDTO;
import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
import com.lakitchen.LA.Kitchen.api.dto.RateDTO;
import com.lakitchen.LA.Kitchen.model.entity.ProductAssessment;
import com.lakitchen.LA.Kitchen.service.global.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssessmentMapper {

    @Autowired
    Func FUNC;

    public AssessmentDTO mapToAssessmentDTO(ProductAssessment assessment) {
        return new AssessmentDTO(
                assessment.getId(),
                new IdNameDTO(assessment.getUser().getId(), assessment.getUser().getName()),
                assessment.getRate().intValue(),
                assessment.getComment(),
                FUNC.getFormatDateSlash(assessment.getCreatedAt()),
                assessment.getDeletedAt()
        );
    }

    public RateDTO mapToRateDTO(Integer five, Integer four, Integer three, Integer two, Integer one) {
        return new RateDTO(five, four, three, two, one);
    }

    public Assessment2DTO mapToAssessment2DTO(ProductAssessment assessment) {
        return new Assessment2DTO(
                assessment.getId(),
                assessment.getUser().getName(),
                assessment.getRate().intValue(),
                assessment.getComment(),
                FUNC.getFormatDateSlash(assessment.getCreatedAt()),
                assessment.getDeletedAt()
        );
    }

}
