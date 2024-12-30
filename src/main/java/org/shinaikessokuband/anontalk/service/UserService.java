package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.entity.User;
import org.shinaikessokuband.anontalk.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    void userOnline(String username);

    void userOffline(String username);

    Set<String> getOnlineUsers();

    void addSession(String username, WebSocketSession session);

    void removeSession(String username);

    WebSocketSession getSession(String username);

    Map<String, WebSocketSession> getUserSessions();

    UserDto getUserByAccount(String userName);

    void deleteUserByAccount(String userName);

    UserDto login(String username, String password);

    boolean logout(String userName);

    @Transactional
    Integer registerNewUser(String phone, String username, String password);

    List<User> getAllUsers();

    int getOnlineUserCount();

    void banUser(String userName);

    void activateUser(String userName);

    String registerNewUser(UserDto userDto);
}
