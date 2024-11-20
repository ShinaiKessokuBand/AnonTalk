package Chatroom;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomDAOImpl implements RoomDAO {
    private Connection conn;

    public RoomDAOImpl(Connection connection){
        this.conn = connection;
    }

    @Override
    public void createRoom(String rname) throws SQLException {
        String query = "insert into chatrooms values(?)";
        try(PreparedStatement statement = conn.prepareStatement(query)){
            statement.setString(1, rname);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void addUserToChatroom(int rid, int uid1, int uid2) throws SQLException {
        String query = "insert into chatrooms (rid, uid1, uid2) values(?, ?, ?)";
        try(PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, rid);
            statement.setInt(2, uid1);
            statement.setInt(3, uid2);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void storeMessage(int rid, int uid, String msg) throws SQLException {
        String query = "insert into messages (rid, uid, message) values(?, ?, ?)";
        try(PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, rid);
            statement.setInt(2, uid);
            statement.setString(3, msg);
            statement.executeUpdate();
        }
    }

    @Override
    public void closeRoom(int rid) throws SQLException {
        //删除用户与聊天室
        String deleteRoom = "DELETE FROM chatrooms WHERE rid = ?";
        try (PreparedStatement statement = conn.prepareStatement(deleteRoom)){
            statement.setInt(1, rid);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
        //删除聊天记录qwq
        String deleteMessage = "DELETE FROM messages WHERE rid = ?";
        try (PreparedStatement statement = conn.prepareStatement(deleteMessage)){
            statement.setInt(1, rid);
            statement.executeUpdate();
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}
