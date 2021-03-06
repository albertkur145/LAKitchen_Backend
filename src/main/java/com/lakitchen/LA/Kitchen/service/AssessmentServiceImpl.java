package com.lakitchen.LA.Kitchen.service;

import com.lakitchen.LA.Kitchen.api.dto.Assessment2DTO;
import com.lakitchen.LA.Kitchen.api.dto.AssessmentDTO;
import com.lakitchen.LA.Kitchen.api.dto.DetailAssessmentDTO;
import com.lakitchen.LA.Kitchen.api.dto.PageableDTO;
import com.lakitchen.LA.Kitchen.api.requestbody.role_user.assessment.AssessmentPostRequest;
import com.lakitchen.LA.Kitchen.api.response.ResponseTemplate;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.assessment.GetAllComment;
import com.lakitchen.LA.Kitchen.api.response.data.role_admin.assessment.GetByProductId;
import com.lakitchen.LA.Kitchen.api.response.data.role_user.assessment.GetAll;
import com.lakitchen.LA.Kitchen.model.entity.OrderDetail;
import com.lakitchen.LA.Kitchen.model.entity.Product;
import com.lakitchen.LA.Kitchen.model.entity.ProductAssessment;
import com.lakitchen.LA.Kitchen.repository.*;
import com.lakitchen.LA.Kitchen.service.global.Func;
import com.lakitchen.LA.Kitchen.service.impl.AssessmentService;
import com.lakitchen.LA.Kitchen.service.mapper.AssessmentMapper;
import com.lakitchen.LA.Kitchen.validation.BasicResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class AssessmentServiceImpl implements AssessmentService {

    @Autowired
    ProductAssessmentRepository assessmentRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderDetailRepository orderDetailRepository;

    @Autowired
    AssessmentMapper assessmentMapper;

    @Autowired
    Func FUNC;

    @Override
    public ResponseTemplate postAssessment(AssessmentPostRequest request) {
        BasicResult result = this.validationPostAssessment(request);

        if (result.getResult()) {
            ProductAssessment assessment = new ProductAssessment();
            OrderDetail orderDetail = orderDetailRepository
                    .findFirstByOrder_OrderNumberAndProduct_Id(request.getOrderNumber(), request.getProductId());
            assessment.setComment(request.getComment());
            assessment.setCreatedAt(FUNC.getCurrentTimestamp());
            assessment.setRate(Double.valueOf(request.getRate()));
            assessment.setUser(userRepository.findFirstById(request.getUserId()));
            assessment.setProduct(productRepository.findFirstById(request.getProductId()));
            orderDetail.setIsAssessment(1);

            assessmentRepository.save(assessment);
            orderDetailRepository.save(orderDetail);
            return new ResponseTemplate(200, "OK", null, null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getAll(Integer productId) {
        BasicResult result = this.validationGetAll(productId);

        if (result.getResult()) {
            ArrayList<AssessmentDTO> assessmentDTOS = new ArrayList<>();
            ArrayList<ProductAssessment> productAssessments = assessmentRepository
                    .findByProduct_IdOrderByCreatedAtDesc(productId);
            productAssessments.forEach((val) -> {
                assessmentDTOS.add(assessmentMapper.mapToAssessmentDTO(val));
            });
            return new ResponseTemplate(200, "OK",
                    new GetAll(assessmentDTOS),
                    null, null);
        }

        return new ResponseTemplate(result.getCode(), result.getStatus(), null, null, result.getError());
    }

    @Override
    public ResponseTemplate getByProductId(Integer productId) {
        Product product = productRepository.findFirstById(productId);

        if (product == null) {
            return new ResponseTemplate(404, "NOT FOUND", null, null, "Produk tidak ditemukan");
        }

        DetailAssessmentDTO detailAssessmentDTO = assessmentRepository
                .findDetailAssessmentByProductId(productId);
        Integer rateFive = assessmentRepository.countSpesificRate(productId, 5);
        Integer rateFour = assessmentRepository.countSpesificRate(productId, 4);
        Integer rateThree = assessmentRepository.countSpesificRate(productId, 3);
        Integer rateTwo = assessmentRepository.countSpesificRate(productId, 2);
        Integer rateOne = assessmentRepository.countSpesificRate(productId, 1);
        GetByProductId response = new GetByProductId(
                product.getName(),
                assessmentMapper.mapToRateDTO(rateFive, rateFour, rateThree, rateTwo, rateOne),
                detailAssessmentDTO.getTotalAssessments(),
                detailAssessmentDTO.getEvaluators(),
                detailAssessmentDTO.getRating(),
                detailAssessmentDTO.getTotalComments()
        );

        return new ResponseTemplate(200, "OK", response, null, null);
    }

    @Override
    public ResponseTemplate getAllComment(Integer productId, Integer page) {
        Product product = productRepository.findFirstById(productId);

        if (product == null) {
            return new ResponseTemplate(404, "NOT FOUND", null, null, "Produk tidak ditemukan");
        }

        Pageable paging = PageRequest.of((page-1), 3, Sort.by("createdAt").descending());
        Page<ProductAssessment> assessments = assessmentRepository.findByProduct_Id(paging, productId);
        ArrayList<Assessment2DTO> dto = this.helperMapToAssessment2DTO(assessments);
        PageableDTO pageableDTO = FUNC.mapToPageableDTO(assessments);
        return new ResponseTemplate(200, "OK", new GetAllComment(dto), pageableDTO, null);
    }

    @Override
    public ResponseTemplate deleteComment(Integer id) {
        ProductAssessment assessment = assessmentRepository.findFirstById(id);
        if (assessment == null) {
            return new ResponseTemplate(404, "NOT FOUND", null, null, null);
        }
        assessment.setDeletedAt(FUNC.getCurrentTimestamp());
        assessmentRepository.save(assessment);
        return new ResponseTemplate(200, "OK", null, null, null);
    }

    private ArrayList<Assessment2DTO> helperMapToAssessment2DTO(Page<ProductAssessment> assessments) {
        ArrayList<Assessment2DTO> dto = new ArrayList<>();
        assessments.getContent().forEach((val) -> {
            dto.add(assessmentMapper.mapToAssessment2DTO(val));
        });
        return dto;
    }

    private BasicResult validationGetAll(Integer productId) {
        if (!FUNC.isExistProduct(productId)) {
            return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
        }
        return new BasicResult(true, null, "OK", 200);
    }

    private BasicResult validationPostAssessment(AssessmentPostRequest request) {
        if (request.getRate() == null || request.getOrderNumber() == null
        || request.getUserId() == null || request.getProductId() == null) {
            return new BasicResult(false, "Form tidak lengkap", "BAD REQUEST", 400);
        }

        if (!FUNC.isExistProduct(request.getProductId())) {
            return new BasicResult(false, "Produk tidak ditemukan", "NOT FOUND", 404);
        }

        if (!FUNC.isExistUser(request.getUserId())) {
            return new BasicResult(false, "User tidak ditemukan", "NOT FOUND", 404);
        }

        if (!this.isExistOrder(request.getOrderNumber(), request.getUserId())) {
            return new BasicResult(false, "Order tidak ditemukan", "NOT FOUND", 404);
        }

        if (!this.isExistProductInOrder(request.getOrderNumber(), request.getProductId())) {
            return new BasicResult(false, "Produk ini tidak kamu beli di order tersebut", "NOT FOUND", 404);
        }

        if (this.isAlreadyAssessment(request.getOrderNumber(), request.getProductId())) {
            return new BasicResult(false, "Produk ini telah diberi penilaian", "NOT ACCEPTABLE", 406);
        }

        if (request.getRate() <= 0) {
            return new BasicResult(false, "Minimal bintang 1", "BAD REQUEST", 400);
        }

        if (request.getRate() > 5) {
            return new BasicResult(false, "Maksimal bintang 5", "BAD REQUEST", 400);
        }

        return new BasicResult(true, null, "OK", 200);
    }

    private Boolean isExistOrder(String orderNumber, Integer userId) {
        return orderRepository
                .findFirstByOrderNumberAndOrderStatus_IdAndUser_Id(orderNumber, 5, userId) != null;
    }

    private Boolean isAlreadyAssessment(String orderNumber, Integer productId) {
        return orderDetailRepository
                .findFirstByOrder_OrderNumberAndProduct_IdAndIsAssessment(orderNumber, productId, 1) != null;
    }

    private Boolean isExistProductInOrder(String orderNumber, Integer productId) {
        return orderDetailRepository
                .findFirstByOrder_OrderNumberAndProduct_Id(
                        orderNumber,
                        productId
                ) != null;
    }

}
