package Chatroom;

import User.User;
import java.util.ArrayList;

public class Chatroom {
    private String rid;
    private String rname;
    private ArrayList<User> users;

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getRid() {
        return rid;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }
    public String getRname() {
        return rname;
    }
}
