package org.shinaikessokuband.anontalk.entity;

import jakarta.persistence.*;

@Table(name="room")
@Entity
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="room_id")
    private Integer roomId;

    @Column(name="room_name")
    private String roomName;

    public Room(String roomName) {
        this.roomName = roomName;
    }

    public Room(){
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }
}
