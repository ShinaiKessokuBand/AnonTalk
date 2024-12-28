package org.shinaikessokuband.anontalk.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.shinaikessokuband.anontalk.entity.Room;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "message")
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Integer messageId;
    @Column(name = "room_id")
    private Integer roomId;
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

    @Column(name = "timestamp")
    private LocalDateTime timestamp;

    public Integer getMessageId() {
        return messageId;
    }

    public void setUsername(String username) {
        this.senderId = username;
    }


    public void setMessageId(Integer messageId) {
        this.messageId = messageId;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public Integer getMessageType() {
        return messageType;
    }

    public void setMessageType(Integer messageType) {
        this.messageType = messageType;
    }

    public String getMessageContent() {
        return messageContent;
    }

    public void setMessage(String messageContent) {
        this.messageContent = messageContent;
    }

    public String getMessageExt() {
        return messageExt;
    }

    public void setMessageExt(String messageExt) {
        this.messageExt = messageExt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}