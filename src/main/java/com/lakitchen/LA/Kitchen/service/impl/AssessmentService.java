package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.role_user.assessment.AssessmentPostRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface AssessmentService {

    ResponseTemplate postAssessment(AssessmentPostRequest request);
    ResponseTemplate getAll(Integer productId);

}
