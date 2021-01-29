package com.lakitchen.LA.Kitchen.controller;

import com.lakitchen.LA.Kitchen.api.path.AssessmentPath;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.assessment.AssessmentPostRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.service.AssessmentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class AssessmentController {

    @Autowired
    AssessmentServiceImpl assessmentService;

    // ROLE_USER
    @PostMapping(AssessmentPath.ASSESSMENT_POST)
    public ResponseTemplate postAssessment(@RequestBody AssessmentPostRequest objParam) {
        return assessmentService.postAssessment(objParam);
    }

    // ROLE_USER
    @GetMapping(AssessmentPath.ASSESSMENT_GET_ALL)
    public ResponseTemplate getAll(@RequestParam("productId") Integer productId) {
        return assessmentService.getAll(productId);
    }

    // ROLE_ADMIN
    @GetMapping(AssessmentPath.ADMIN_ASSESSMENT_GET_DETAIL)
    public ResponseTemplate getByProductId(@RequestParam("productId") Integer productId) {
        return assessmentService.getByProductId(productId);
    }

    // ROLE_ADMIN
    @GetMapping(AssessmentPath.ADMIN_ASSESSMENT_GET_ALL_COMMENT)
    public ResponseTemplate getAllComment(@RequestParam("productId") Integer productId,
                                          @RequestParam("page") Integer page) {
        return assessmentService.getAllComment(productId, page);
    }

    // ROLE_ADMIN
    @DeleteMapping(AssessmentPath.ADMIN_ASSESSMENT_DELETE_COMMENT)
    public ResponseTemplate deleteComment(@RequestParam("id") Integer id) {
        return assessmentService.deleteComment(id);
    }

}
