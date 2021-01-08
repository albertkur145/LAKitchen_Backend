package com.lakitchen.LA.Kitchen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lakitchen.LA.Kitchen.model.constant.PaymentMethodConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = PaymentMethodConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = PaymentMethodConstant.ID)
})
public class PaymentMethod implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = PaymentMethodConstant.ID)
    private Integer id;

    @Column(name = PaymentMethodConstant.NAME)
    private String name;

    @Column(name = PaymentMethodConstant.NUMBER)
    private String number;

    @JsonIgnore
    @OneToMany(mappedBy = "paymentMethod", fetch = FetchType.LAZY)
    private Set<Payment> payments;
}
