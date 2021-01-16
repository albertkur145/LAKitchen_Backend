package com.lakitchen.LA.Kitchen.service.mapper;

import com.lakitchen.LA.Kitchen.api.dto.AssessmentDTO;
import com.lakitchen.LA.Kitchen.api.dto.IdNameDTO;
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
                FUNC.getFormatDateSlash(assessment.getCreatedAt())
        );
    }

}
