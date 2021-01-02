package com.lakitchen.LA.Kitchen.model.entity;

import com.lakitchen.LA.Kitchen.model.constant.ShopConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = ShopConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = ShopConstant.ID)
})
public class Shop implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ShopConstant.ID)
    private Integer id;

    @Column(name = ShopConstant.NAME)
    private String name;

    @Column(name = ShopConstant.ADDRESS)
    private String address;

    @Column(name = ShopConstant.PHONE_NUMBER)
    private String phoneNumber;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ShopConstant.USER_ID)
    private User user;

    @OneToMany(mappedBy = "shop", fetch = FetchType.LAZY)
    private Set<Product> products;
}
