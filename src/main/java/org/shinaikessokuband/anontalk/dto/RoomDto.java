package org.shinaikessokuband.anontalk.dto;

import jakarta.validation.constraints.NotBlank;

public class RoomDto {
    @NotBlank
    private String roomName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public RoomDto( String roomName ) {
        this.roomName = roomName;
    }

    public RoomDto() {}
}
