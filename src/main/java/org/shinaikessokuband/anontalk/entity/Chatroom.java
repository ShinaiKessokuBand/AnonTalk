package org.shinaikessokuband.anontalk.entity;

import lombok.Data;
import org.shinaikessokuband.anontalk.entity.User;
import java.util.ArrayList;

@Data
public class Chatroom {
    private String roomId;
    private String roomName;
    private ArrayList<User> users;

    public void setRid(String rid) {
        this.roomId = rid;
    }

    public String getRid() {
        return roomName;
    }

    public void setRname(String rname) {
        this.roomName = rname;
    }

    public String getRname() {
        return roomName;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    public ArrayList<User> getUsers() {
        return users;
    }
}
