package com.lakitchen.LA.Kitchen.model.entity;

import com.lakitchen.LA.Kitchen.model.constant.WishlistConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = WishlistConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = WishlistConstant.USER_ID),
        @UniqueConstraint(columnNames = WishlistConstant.PRODUCT_ID)
})
public class Wishlist implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = WishlistConstant.USER_ID)
    private User user;

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = WishlistConstant.PRODUCT_ID)
    private Product product;

    @Column(name = WishlistConstant.CREATED_AT)
    private Timestamp createdAt;
}
