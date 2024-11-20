package Chatroom;

import java.sql.SQLException;

public interface RoomDAO {
    void createRoom(String rname) throws SQLException;//创建聊天室

    void addUserToChatroom(int rid, int uid1, int uid2) throws SQLException;//添加匹配到的用户到聊天室

    void storeMessage(int rid, int uid, String msg) throws SQLException;//存储聊天记录

    void closeRoom(int rid) throws SQLException;
}
