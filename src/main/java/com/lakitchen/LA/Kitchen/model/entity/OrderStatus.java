package com.lakitchen.LA.Kitchen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lakitchen.LA.Kitchen.model.constant.OrderStatusConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = OrderStatusConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = OrderStatusConstant.ID)
})
public class OrderStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = OrderStatusConstant.ID)
    private Integer id;

    @Column(name = OrderStatusConstant.NAME)
    private String name;

    @JsonIgnore
    @OneToMany(mappedBy = "orderStatus", fetch = FetchType.LAZY)
    private Set<Order> orders;
}
