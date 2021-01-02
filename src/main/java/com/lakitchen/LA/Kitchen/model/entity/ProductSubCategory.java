package com.lakitchen.LA.Kitchen.model.entity;

import com.lakitchen.LA.Kitchen.model.constant.ProductSubCategoryConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = ProductSubCategoryConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = ProductSubCategoryConstant.ID)
})
public class ProductSubCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ProductSubCategoryConstant.ID)
    private Integer id;

    @Column(name = ProductSubCategoryConstant.NAME)
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ProductSubCategoryConstant.CATEGORY_ID)
    private ProductCategory productCategory;

    @OneToMany(mappedBy = "productSubCategory", fetch = FetchType.LAZY)
    private Set<Product> products;
}
