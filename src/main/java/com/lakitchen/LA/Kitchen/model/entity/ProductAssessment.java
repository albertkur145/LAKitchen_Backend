package com.lakitchen.LA.Kitchen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lakitchen.LA.Kitchen.model.constant.ProductAssessmentConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = ProductAssessmentConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = ProductAssessmentConstant.ID)
})
public class ProductAssessment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ProductAssessmentConstant.ID)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ProductAssessmentConstant.PRODUCT_ID)
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ProductAssessmentConstant.USER_ID)
    private User user;

    @Column(name = ProductAssessmentConstant.RATE)
    private Double rate;

    @Column(name = ProductAssessmentConstant.COMMENT)
    private String comment;

    @Column(name = ProductAssessmentConstant.CREATED_AT)
    private Timestamp createdAt;

    @Column(name = ProductAssessmentConstant.DELETED_AT)
    private Timestamp deletedAt;
}
