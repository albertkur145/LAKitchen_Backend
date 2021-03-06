package com.lakitchen.LA.Kitchen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lakitchen.LA.Kitchen.model.constant.UserConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Table(name = UserConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = UserConstant.ID),
        @UniqueConstraint(columnNames = UserConstant.EMAIL),
        @UniqueConstraint(columnNames = UserConstant.PHONE_NUMBER)
})
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = UserConstant.ID)
    private Integer id;

    @Column(name = UserConstant.NAME)
    private String name;

    @Column(name = UserConstant.EMAIL)
    private String email;

    @Column(name = UserConstant.PASSWORD)
    private String password;

    @Column(name = UserConstant.PHONE_NUMBER)
    private String phoneNumber;

    @Column(name = UserConstant.ADDRESS)
    private String address;

    @Column(name = UserConstant.PROVINCE)
    private String province;

    @Column(name = UserConstant.CITY)
    private String city;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = UserConstant.USER_STATUS_ID)
    private UserStatus userStatus;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = UserConstant.ROLE_ID)
    private UserRole userRole;

    @Column(name = UserConstant.CREATED_AT)
    private Timestamp createdAt;

    @Column(name = UserConstant.UPDATED_AT)
    private Timestamp updatedAt;

    @JsonIgnore
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Shop shop;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<ProductAssessment> productAssessments;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Call> userCalls;

    @JsonIgnore
    @OneToMany(mappedBy = "cs", fetch = FetchType.LAZY)
    private Set<Call> csCalls;

    @JsonIgnore
    @OneToMany(mappedBy = "from", fetch = FetchType.LAZY)
    private Set<Conversation> userMessageFrom;

    @JsonIgnore
    @OneToMany(mappedBy = "to", fetch = FetchType.LAZY)
    private Set<Conversation> userMessageTo;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Wishlist> wishlists;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Cart> carts;

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<Order> orders;
}




