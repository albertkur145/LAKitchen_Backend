package com.lakitchen.LA.Kitchen.model.entity;

import com.lakitchen.LA.Kitchen.model.constant.OrderConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Table(name = OrderConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = OrderConstant.ORDER_NUMBER)
})
public class Order implements Serializable {

    @Id
    @Column(name = OrderConstant.ORDER_NUMBER)
    private String orderNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = OrderConstant.USER_ID)
    private User user;

    @Column(name = OrderConstant.CREATED_AT)
    private Timestamp createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = OrderConstant.ORDER_STATUS_ID)
    private OrderStatus orderStatus;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY)
    private Set<OrderDetail> orderDetails;

    @OneToOne(mappedBy = "order", fetch = FetchType.LAZY)
    private Payment payment;
}
