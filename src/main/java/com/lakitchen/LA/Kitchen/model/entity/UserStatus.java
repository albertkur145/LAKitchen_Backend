package com.lakitchen.LA.Kitchen.model.entity;

import com.lakitchen.LA.Kitchen.model.constant.UserStatusConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = UserStatusConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = UserStatusConstant.ID)
})
public class UserStatus implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = UserStatusConstant.ID)
    private Integer id;

    @Column(name = UserStatusConstant.NAME)
    private String name;

    @OneToMany(mappedBy = "userStatus", fetch = FetchType.LAZY)
    private Set<User> users;
}
