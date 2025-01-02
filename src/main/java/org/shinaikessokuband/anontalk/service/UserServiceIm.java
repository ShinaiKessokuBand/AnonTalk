package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.converter.UserConverter;
import org.shinaikessokuband.anontalk.dto.UserRegDto;
import org.shinaikessokuband.anontalk.repository.UserRepository;
import org.shinaikessokuband.anontalk.dto.UserDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.shinaikessokuband.anontalk.entity.User;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.socket.WebSocketSession;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;

@Service
public class UserServiceIm implements UserService {


    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDto getUserByAccount(String userName) {
        List<User> res = userRepository.findByUsername(userName);
        if(!res.isEmpty()) {
            return UserConverter.convertUser(res.get(0));
        }
        return null;
    }

    @Override
    public void deleteUserByAccount(String userName) {
        List<User> res = userRepository.findByUsername(userName);

        if (res != null)
        {
            User user = res.getFirst();
            userRepository.deleteByUserId(user.getUserId());
        }
        else
            throw new IllegalArgumentException("account: " + userName + " does not exist");
    }


    private static final Logger logger = LoggerFactory.getLogger(UserServiceIm.class);

    @Autowired
    private DataSource dataSource;

    @Override
    public int login(String username, String password) {
        // Check database connection
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                logger.info("Database connection is successful.");
            } else {
                logger.error("Database connection failed.");
                return -1;
            }
        } catch (SQLException e) {
            logger.error("Database connection failed: " + e.getMessage());
            return -1;
        }

        // Log in logic
        logger.info("Attempting to log in with username: {}", username);
        List<User> users = userRepository.findByUsername(username);
        if (!users.isEmpty()) {
            User user = users.get(0); // Assuming username is unique and taking the first match
            if (user.getPassword().equals(password)) {
                user.setOnline(true);
                logger.info("Login successful for username: {}", username);
                return user.getUserId();
            } else {
                logger.warn("Login failed for username: {}", username);
                return -1;
            }
        } else {
            logger.warn("No user found with username: {}", username);
            return -1;
        }
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
        if(!userRepository.findByUsername(username).isEmpty()){
            logger.error("Username: " + username + " has been taken.");
            return -1;
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

    @Transactional
    public int updateUserInfo(int userid, String username, String gender, String hobbies) {
        List<User> result = userRepository.findByUserId(userid);
        if (result.isEmpty()) {
            logger.error("User with id: " + userid + " does not exist.");
            return -1;
        }
        User user = result.getFirst();
        user.setGender(gender);
        user.setHobbies(hobbies);
        if(!username.isEmpty()) {
            user.setUsername(username);
        }
        userRepository.save(user);
        return 0;
    }

    @Transactional
    public int updateUserSecurity(int userid, String password, String phone) {
        List<User> result = userRepository.findByUserId(userid);
        if (result.isEmpty()) {
            logger.error("User with id: " + userid + " does not exist.");
            return -1;
        }
        User user = result.getFirst();
        if(!password.isEmpty()) {
            user.setPassword(password);
        }
        if(!phone.isEmpty()) {
            user.setPhoneNumber(phone);
        }
        userRepository.save(user);
        return 0;
    }
}