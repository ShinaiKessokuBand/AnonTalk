package org.shinaikessokuband.anontalk.repository;

import java.sql.*;
import org.shinaikessokuband.anontalk.entity.User;

public class UserDAOImpl implements UserDAO {
    private Connection conn;

    public UserDAOImpl(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void addUser(User user) throws SQLException {
        if (user.getUsername() == null || user.getPassword() == null || user.getUsername().isEmpty() ||
                user.getPassword().isEmpty()) {
            throw new IllegalArgumentException("昵称和密码是必填的！");
        }

        String query = "insert into userinfo (uname, psw, age, gender, hobby, email, phone)values(?,?,?,?,?,?,?)";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, user.getUsername());//昵称
            statement.setString(2, user.getPassword());//密码
//            设置可选字段，若空则传null
            if (user.getAge() != 0) {
                statement.setInt(3, user.getAge());
            } else{
                statement.setInt(3, Types.INTEGER);
            }

            if (user.getGender() != null && !user.getGender().isEmpty()) {
                statement.setString(4, user.getGender());
            } else{
                statement.setNull(4, Types.VARCHAR);
            }
/*
            if (user.getHobby() != null && !user.getHobby().isEmpty()) {
                statement.setString(5, user.getHobby());
            } else{
                statement.setNull(5, Types.VARCHAR);
            }
            */


            if (user.getEmail() != null && !user.getEmail().isEmpty()) {
                statement.setString(6, user.getEmail());
            } else{
                statement.setNull(6, Types.VARCHAR);
            }
/*
            if (user.getPhone() != null && !user.getPhone().isEmpty()) {
                statement.setString(7, user.getPhone());
            } else{
                statement.setNull(7, Types.VARCHAR);
            }
*/
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUser(int id){
        String query = "delete from userinfo where uid = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)){
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateUser(User user) {
        String query = "UPDATE userinfo SET uname = ?, psw = ?, age = ?, hobby = ? WHERE uid = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getPassword());
            statement.setInt(3, user.getAge());
        //    statement.setString(4, user.getHobby());
        //    statement.setInt(5, user.getUid());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public User getUser(int id) {
        String query = "select * from userinfo where uid = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                User user = new User();
            //    user.setUid(rs.getInt("uid"));
                user.setAge(rs.getInt("age"));
                user.setGender(rs.getString("gender"));
            //    user.setHobby(rs.getString("hobby"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
