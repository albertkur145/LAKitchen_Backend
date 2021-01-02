package com.lakitchen.LA.Kitchen.model.entity;

import com.lakitchen.LA.Kitchen.model.constant.ProductPhotoConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = ProductPhotoConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = ProductPhotoConstant.ID)
})
public class ProductPhoto implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ProductPhotoConstant.ID)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ProductPhotoConstant.PRODUCT_ID)
    private Product product;

    @Column(name = ProductPhotoConstant.LINK)
    private String link;
}
