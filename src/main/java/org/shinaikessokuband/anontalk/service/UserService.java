package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.entity.User;
import org.shinaikessokuband.anontalk.dto.UserDto;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {

    String registerNewUser(UserDto userDto);//register

    void userOnline(String username);

    void userOffline(String username);

    Set<String> getOnlineUsers();

    void addSession(String username, WebSocketSession session);

    void removeSession(String username);

    WebSocketSession getSession(String username);

    Map<String, WebSocketSession> getUserSessions();

    UserDto getUserByAccount(String account);

    void deleteUserByAccount(String account);

    UserDto login(String account, String password);

    boolean logout(String account);

    List<User> getAllUsers();

    int getOnlineUserCount();

    void banUser(String account);

    void activateUser(String account);
}
