package com.lakitchen.LA.Kitchen.api.dto;

import java.sql.Timestamp;

public interface DetailAssessmentDTO {
    Integer getTotalAssessments();
    Integer getEvaluators();
    Double getRating();
    Integer getTotalComments();
}
