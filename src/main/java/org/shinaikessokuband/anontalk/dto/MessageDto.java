package org.shinaikessokuband.anontalk.dto;

import java.time.LocalDateTime;

public class MessageDto {
    private Integer roomId;
    private String username;
    private String message;
    private LocalDateTime timestamp;

    public MessageDto(Integer roomId, String username, String message) {
        this.roomId = roomId;
        this.username = username;
        this.message = message;
    }

    public MessageDto() {}

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
