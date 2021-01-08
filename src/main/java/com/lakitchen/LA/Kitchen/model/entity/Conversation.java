package com.lakitchen.LA.Kitchen.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lakitchen.LA.Kitchen.model.constant.ConversationConstant;
import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Table(name = ConversationConstant.TABLE_NAME, uniqueConstraints = {
        @UniqueConstraint(columnNames = ConversationConstant.ID)
})
public class Conversation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = ConversationConstant.ID)
    private Integer id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ConversationConstant.FROM)
    private User from;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ConversationConstant.TO)
    private User to;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = ConversationConstant.CALL_ID)
    private Call call;

    @Column(name = ConversationConstant.MESSAGE)
    private String message;

    @Column(name = ConversationConstant.IS_READ)
    private Boolean isRead;

    @Column(name = ConversationConstant.CREATED_AT)
    private Timestamp createdAt;
}
