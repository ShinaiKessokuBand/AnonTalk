package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.converter.UserConverter;
import org.shinaikessokuband.anontalk.repository.UserRepository;
import org.shinaikessokuband.anontalk.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.shinaikessokuband.anontalk.entity.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Service
public class UserServiceIm implements UserService {

    private final Map<String, WebSocketSession> userSessions = Collections.synchronizedMap(new HashMap<>());

    @Override
    public void addSession(String username, WebSocketSession session) {
        userSessions.put(username, session);
    }
    @Override
    public void removeSession(String username) {
        userSessions.remove(username);
    }
    @Override
    public WebSocketSession getSession(String username) {
        return userSessions.get(username);
    }
    @Override
    public Map<String, WebSocketSession> getUserSessions() {
        return userSessions;
    }
    private final Set<String> onlineUsers = Collections.synchronizedSet(new HashSet<String>());

    @Override
    public void userOnline(String username) {
        onlineUsers.add(username);
    }
    @Override
    public void userOffline(String username) {
        onlineUsers.remove(username);
    }
    @Override
    public Set<String> getOnlineUsers() {
        return onlineUsers;
    }

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getUserByAccount(String userName) {
        User user = (User) userRepository.findByUsername(userName);

        if (user!=null){
            return UserConverter.convertUser(user);
        }
        return null;
    }

    @Override
    public void deleteUserByAccount(String userName) {
        User user = (User) userRepository.findByUsername(userName);
        if (user!=null)
        {
            throw new IllegalArgumentException("account: " + userName + " does not exist");
        }
        else userRepository.deleteByUserId(user.getUserId());
    }



    @Override
    public UserDto login(String username, String password) {
        User user = (User) userRepository.findByUsername(username);
        if (user != null&& user.getPassword().equals(password)) {
            user.setOnline(true);
            return UserConverter.convertUser(user);
        }else
            throw new IllegalArgumentException("user: " + username + " does not exist");
    }

    @Override
    public boolean logout(String userName) {
        User user = (User) userRepository.findByUsername(userName);
        if (user != null) {
            user.setOnline(false);
            return true;
        }
        return false;
    }

    @Transactional
    @Override
    public Integer registerNewUser(String phone, String username, String password) {
        UserDto userDto = new UserDto();
        userDto.setPhoneNumber(phone);
        userDto.setUsername(username);
        userDto.setPassword(password);
        if(userRepository.findByUsername(username) != null){
            throw new IllegalStateException("Username: " + username + " has been taken.");
        }
        List<User> userList = userRepository.findByEmail(userDto.getEmail());
        if (!CollectionUtils.isEmpty(userList)) {
            throw new IllegalStateException("Email: " + userDto.getEmail() + " has been taken.");
        }
        List<User> userList1 = userRepository.findByPhoneNumber(userDto.getPhoneNumber());
        if (!CollectionUtils.isEmpty(userList1)) {
            throw new IllegalStateException("PhoneNumber: " + userDto.getPhoneNumber() + " has been taken.");
        }
        User user = userRepository.save(UserConverter.convertUserDto(userDto));
        return user.getUserId();
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(); // 获取所有用户
    }

    @Override
    public int getOnlineUserCount() {
        List<User> userList = userRepository.findAll();
        //get all online users
        int count = 0;
        for (User user : userList) {
            if (user.isOnline()) {
                count++;
            }
        }
        return count /* 在线用户数量 */;
    }

    @Override
    public void banUser(String userName) {
        User user = (User) userRepository.findByUsername(userName);
        if (user != null) {
            user.setBanned(true); // 设置用户为封禁状态
            userRepository.save(user); // 保存用户状态
        }
    }

    @Override
    public void activateUser(String userName) {
        User user = (User) userRepository.findByUsername(userName);
        if (user != null) {
            user.setBanned(false); // 设置用户为解封状态
            userRepository.save(user); // 保存用户状态
        }
    }

    @Override
    @Transactional
    public String registerNewUser(UserDto userDto) {
        if (userRepository.findByUsername(userDto.getUsername()) != null) {
            throw new IllegalStateException("Username: " + userDto.getUsername() + " has been taken.");
        }
        List<User> userList = userRepository.findByEmail(userDto.getEmail());
        if (!CollectionUtils.isEmpty(userList)) {
            throw new IllegalStateException("Email: " + userDto.getEmail() + " has been taken.");
        }
        List<User> userList1 = userRepository.findByPhoneNumber(userDto.getPhoneNumber());
        if (!CollectionUtils.isEmpty(userList1)) {
            throw new IllegalStateException("PhoneNumber: " + userDto.getPhoneNumber() + " has been taken.");
        }
        User user = UserConverter.convertUserDto(userDto);
        user = userRepository.save(user);
        return user.getUsername();
    }



}
