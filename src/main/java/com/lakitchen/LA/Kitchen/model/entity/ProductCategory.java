package com.lakitchen.LA.Kitchen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lakitchen.LA.Kitchen.model.constant.ProductCategoryConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = ProductCategoryConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = ProductCategoryConstant.ID),
        @UniqueConstraint(columnNames = ProductCategoryConstant.NAME)
})
public class ProductCategory implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ProductCategoryConstant.ID)
    private Integer id;

    @Column(name = ProductCategoryConstant.NAME)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "productCategory", fetch = FetchType.LAZY)
    private Set<ProductSubCategory> productSubCategories;
}
