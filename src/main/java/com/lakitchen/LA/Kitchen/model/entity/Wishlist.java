package com.lakitchen.LA.Kitchen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lakitchen.LA.Kitchen.model.constant.WishlistConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = WishlistConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = WishlistConstant.ID)
})
public class Wishlist implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = WishlistConstant.ID)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = WishlistConstant.USER_ID)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = WishlistConstant.PRODUCT_ID)
    private Product product;

    @Column(name = WishlistConstant.CREATED_AT)
    private Timestamp createdAt;
}
