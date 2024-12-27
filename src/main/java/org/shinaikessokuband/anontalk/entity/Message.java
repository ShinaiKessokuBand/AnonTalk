package org.shinaikessokuband.anontalk.entity;

import jakarta.persistence.*;
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

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
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

    public void setMessageContent(String messageContent) {
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
}