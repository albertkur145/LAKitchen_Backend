package com.lakitchen.LA.Kitchen.model.entity;

import com.lakitchen.LA.Kitchen.model.constant.CallConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;

@Entity
@Data
@Table(name = CallConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = CallConstant.ID)
})
public class Call implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = CallConstant.ID)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CallConstant.USER_ID)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = CallConstant.CS_ID)
    private User cs;

    @Column(name = CallConstant.IS_ENDED)
    private Boolean isEnded;

    @Column(name = CallConstant.CREATED_AT)
    private Timestamp createdAt;

    @OneToMany(mappedBy = "call", fetch = FetchType.LAZY)
    private Set<Conversation> conversations;
}
