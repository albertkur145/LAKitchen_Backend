package com.lakitchen.LA.Kitchen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lakitchen.LA.Kitchen.model.constant.CartConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = CartConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = CartConstant.ID)
})
public class Cart implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = CartConstant.ID)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CartConstant.USER_ID)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CartConstant.PRODUCT_ID)
    private Product product;

    @Column(name = CartConstant.QUANTITY)
    private Integer quantity;

    @Column(name = CartConstant.NOTE)
    private String note;

    @Column(name = CartConstant.CREATED_AT)
    private Timestamp createdAt;
}
