package org.shinaikessokuband.anontalk.repository;

import org.shinaikessokuband.anontalk.entity.Message;
import org.shinaikessokuband.anontalk.entity.User;

import java.sql.SQLException;

public interface UserDAO {
    void addUser(User user) throws SQLException;//新建用户


    void deleteUser(int id);//账号已注销

    void updateUser(User user);//修改用户信息

//    void sendMessage(Message message);//发送消息

//    void withdrawMessage(Message message);//撤回消息

    User getUser(int id);//获取用户的信息（或许可以用来匹配）
}
