package com.lakitchen.LA.Kitchen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lakitchen.LA.Kitchen.model.constant.PaymentConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = PaymentConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = PaymentConstant.ORDER_NUMBER)
})
public class Payment implements Serializable {

    @JsonIgnore
    @Id
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PaymentConstant.ORDER_NUMBER)
    private Order order;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = PaymentConstant.PAYMENT_METHOD_ID)
    private PaymentMethod paymentMethod;

    @Column(name = PaymentConstant.TOTAL)
    private Integer total;

    @Column(name = PaymentConstant.CREATED_AT)
    private Timestamp createdAt;
}
