package com.lakitchen.LA.Kitchen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lakitchen.LA.Kitchen.model.constant.ProductConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Table(name = ProductConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = ProductConstant.ID)
})
public class Product implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ProductConstant.ID)
    private Integer id;

    @Column(name = ProductConstant.NAME)
    private String name;

    @Column(name = ProductConstant.PRICE)
    private Integer price;

    @Column(name = ProductConstant.DESCRIPTION, columnDefinition = "TEXT")
    private String description;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ProductConstant.SUB_CATEGORY_ID)
    private ProductSubCategory productSubCategory;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ProductConstant.SHOP_ID)
    private Shop shop;

    @Column(name = ProductConstant.SEEN)
    private Integer seen;

    @Column(name = ProductConstant.CREATED_AT)
    private Timestamp createdAt;

    @Column(name = ProductConstant.UPDATED_AT)
    private Timestamp updatedAt;

    @Column(name = ProductConstant.DELETED_AT)
    private Timestamp deletedAt;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductPhoto> productPhotos;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<ProductAssessment> productAssessments;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Wishlist> wishlists;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY)
    private Set<Cart> carts;

//    @OneToOne(mappedBy = "product", fetch = FetchType.LAZY)
//    private OrderDetail orderDetail;
}
