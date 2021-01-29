package com.lakitchen.LA.Kitchen.service.impl;

import com.lakitchen.LA.Kitchen.api.requestbody.role_user.assessment.AssessmentPostRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;

public interface AssessmentService {

    // ROLE_USER
    ResponseTemplate postAssessment(AssessmentPostRequest request);
    ResponseTemplate getAll(Integer productId);

    // ROLE_ADMIN
    ResponseTemplate getByProductId(Integer productId);
    ResponseTemplate getAllComment(Integer productId, Integer page);
    ResponseTemplate deleteComment(Integer id);
}
