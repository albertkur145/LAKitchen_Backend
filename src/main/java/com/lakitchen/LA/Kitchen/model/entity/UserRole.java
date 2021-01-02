package com.lakitchen.LA.Kitchen.model.entity;

import com.lakitchen.LA.Kitchen.model.constant.UserRoleConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Data
@Table(name = UserRoleConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = UserRoleConstant.ID)
})
public class UserRole implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = UserRoleConstant.ID)
    private Integer id;

    @Column(name = UserRoleConstant.NAME)
    private String name;

    @OneToMany(mappedBy = "userRole", fetch = FetchType.LAZY)
    private Set<User> users;
}
