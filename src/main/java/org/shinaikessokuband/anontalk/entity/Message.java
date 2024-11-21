package org.shinaikessokuband.anontalk.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.Date;

@Data
@Entity
@Table(name = "message")
public class Message {

    @Id
    @Column(name = "message_id")
    private String messageId;
    @Column(name = "room_id")
    private String roomId;
    @Column(name = "sender_id")
    private String senderId;
    @Column(name = "message_type")
    private Integer messageType;
    @Column(name = "message_content")
    private String messageContent;
    @Column(name = "message_ext")
    private String messageExt;
    @Column(name = "status")
    private Integer status;
    @Column(name = "create_time")
    private Date createTime;
}