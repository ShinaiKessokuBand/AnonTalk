package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.dto.UserRegDto;
import org.shinaikessokuband.anontalk.entity.User;
import org.shinaikessokuband.anontalk.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {


    UserDto getUserByAccount(String userName);

    void deleteUserByAccount(String userName);

    int login(String username, String password);

    boolean logout(String userName);

    @Transactional
    Integer registerNewUser(String phone, String username, String password);

    List<User> getAllUsers();

    int getOnlineUserCount();

    void banUser(String userName);

    void activateUser(String userName);

    String registerNewUser(UserRegDto userDto);
}
