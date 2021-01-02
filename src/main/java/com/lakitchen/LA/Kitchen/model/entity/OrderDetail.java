package com.lakitchen.LA.Kitchen.model.entity;

import com.lakitchen.LA.Kitchen.model.constant.OrderDetailConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Table(name = OrderDetailConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = OrderDetailConstant.ORDER_NUMBER),
        @UniqueConstraint(columnNames = OrderDetailConstant.PRODUCT_ID)
})
public class OrderDetail implements Serializable {

    @Id
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = OrderDetailConstant.ORDER_NUMBER)
    private Order order;

    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = OrderDetailConstant.PRODUCT_ID)
    private Product product;

    @Column(name = OrderDetailConstant.PRICE)
    private Integer price;

    @Column(name = OrderDetailConstant.QUANTITY)
    private Integer quantity;

    @Column(name = OrderDetailConstant.NOTE)
    private String note;

    @Column(name = OrderDetailConstant.SUB_TOTAL)
    private Integer subTotal;

    @Column(name = OrderDetailConstant.IS_ASSESSMENT)
    private Boolean isAssessment;
}
