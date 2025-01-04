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
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * 用户服务实现类，提供用户相关的业务逻辑。
 */
@Service
public class UserServiceIm implements UserService {

    private final UserRepository userRepository; // 用户存储库，用于与用户数据交互

    private final DataSource dataSource; // 数据源，用于数据库连接

    private static final Logger logger = LoggerFactory.getLogger(UserServiceIm.class); // 日志记录器

    /**
     * 构造函数，通过依赖注入获取用户存储库和数据源的实例。
     *
     * @param userRepository 用户存储库
     * @param dataSource     数据源
     */
    public UserServiceIm(UserRepository userRepository, DataSource dataSource) {
        this.userRepository = userRepository;
        this.dataSource = dataSource;
    }

    @Override
    public UserDto getUserByAccount(String userName) {
        List<User> res = userRepository.findByUsername(userName); // 根据用户名查找用户
        if (!res.isEmpty()) {
            return UserConverter.convertUser(res.get(0)); // 转换为 UserDto 并返回
        }
        return null; // 如果未找到用户，返回 null
    }

    @Override
    public void deleteUserByAccount(String userName) {
        List<User> res = userRepository.findByUsername(userName); // 查找用户

        if (res != null && !res.isEmpty()) {
            User user = res.get(0); // 获取第一个用户
            userRepository.deleteByUserId(user.getUserId()); // 根据用户 ID 删除用户
        } else {
            throw new IllegalArgumentException("账户: " + userName + " 不存在"); // 账户不存在异常
        }
    }

    @Override
    public int login(String username, String password) {
        // 检查数据库连接
        try (Connection connection = dataSource.getConnection()) {
            if (connection.isValid(2)) {
                logger.info("数据库连接成功。");
            } else {
                logger.error("数据库连接失败。");
                return -1; // 连接失败返回 -1
            }
        } catch (SQLException e) {
            logger.error("数据库连接失败: {}", e.getMessage());
            return -1; // 连接异常返回 -1
        }

        // 登录逻辑
        logger.info("尝试使用用户名登录: {}", username);
        List<User> users = userRepository.findByUsername(username); // 查找用户
        if (!users.isEmpty()) {
            User user = users.get(0); // 假设用户名唯一，获取第一个匹配的用户
            if (user.getPassword().equals(password)) { // 验证密码
                if (user.isBanned()) { // 检查用户是否被封禁
                    logger.warn("用户名为: {} 的用户已被封禁。", username);
                    return -1; // 被封禁返回 -1
                }
                user.setOnline(true); // 设置用户为在线状态
                logger.info("用户名: {} 登录成功。", username);
                return user.getUserId(); // 返回用户 ID
            } else {
                logger.warn("用户名: {} 登录失败，密码错误。", username);
                return -1; // 密码错误返回 -1
            }
        } else {
            logger.warn("用户名: {} 未找到。", username);
            return -1; // 用户未找到返回 -1
        }
    }

    @Override
    public boolean logout(String userName) {
        List<User> users = userRepository.findByUsername(userName); // 查找用户
        if (!users.isEmpty()) {
            User user = users.get(0); // 获取第一个用户
            user.setOnline(false); // 设置用户为离线状态
            userRepository.save(user); // 保存更改
            return true; // 登出成功
        }
        return false; // 用户未找到，登出失败
    }

    /**
     * 注册新用户，事务性操作。
     *
     * @param phone    用户手机号
     * @param username 用户名
     * @param password 用户密码
     * @return 新用户 ID
     */
    @Transactional
    @Override
    public Integer registerNewUser(String phone, String username, String password) {
        UserDto userDto = new UserDto();
        userDto.setPhoneNumber(phone);
        userDto.setUsername(username);
        userDto.setPassword(password);
        if (!userRepository.findByUsername(username).isEmpty()) {
            logger.error("用户名: {} 已被占用。", username);
            return -1; // 用户名已存在返回 -1
        }
        User user = userRepository.save(UserConverter.convertUserDto(userDto)); // 保存新用户
        return user.getUserId(); // 返回新用户 ID
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll(); // 获取所有用户
    }

    @Override
    public int getOnlineUserCount() {
        List<User> userList = userRepository.findAll(); // 获取所有用户
        int count = 0;
        // 统计在线用户
        for (User user : userList) {
            if (user.isOnline()) {
                count++;
            }
        }
        return count; // 返回在线用户数量
    }

    @Override
    public void banUser(String userName) {
        List<User> users = userRepository.findByUsername(userName); // 查找用户
        if (!users.isEmpty()) {
            User user = users.get(0); // 获取第一个用户
            user.setBanned(true); // 设置用户为封禁状态
            userRepository.save(user); // 保存用户状态
        }
    }

    @Override
    public void activateUser(String userName) {
        List<User> users = userRepository.findByUsername(userName); // 查找用户
        if (!users.isEmpty()) {
            User user = users.get(0); // 获取第一个用户
            user.setBanned(false); // 设置用户为解封状态
            userRepository.save(user); // 保存用户状态
        }
    }

    /**
     * 更新用户信息，事务性操作。
     *
     * @param userid   用户 ID
     * @param username 新用户名
     * @param gender   性别
     * @param hobbies  爱好
     * @return 0 表示成功，-1 表示用户不存在
     */
    @Transactional
    public int updateUserInfo(int userid,
                              String username,
                              String gender,
                              String hobbies)
    {
        List<User> result = userRepository.findByUserId(userid); // 根据用户 ID 查找用户
        if (result.isEmpty()) {
            logger.error("ID 为: {} 的用户不存在。", userid);
            return -1; // 用户不存在返回 -1
        }
        User user = result.get(0); // 获取用户
        user.setGender(gender); // 设置性别
        user.setHobbies(hobbies); // 设置爱好
        if (!username.isEmpty()) {
            user.setUsername(username); // 设置用户名
        }
        userRepository.save(user); // 保存用户信息
        return 0; // 返回成功
    }

    /**
     * 更新用户安全信息，事务性操作。
     *
     * @param userid   用户 ID
     * @param password 新密码
     * @param phone    新手机号
     * @return 0 表示成功，-1 表示用户不存在
     */
    @Transactional
    public int updateUserSecurity(int userid,
                                  String password,
                                  String phone)
    {
        List<User> result = userRepository.findByUserId(userid); // 根据用户 ID 查找用户
        if (result.isEmpty()) {
            logger.error("ID 为: {} 的用户不存在。", userid);
            return -1; // 用户不存在返回 -1
        }
        User user = result.get(0); // 获取用户
        if (!password.isEmpty()) {
            user.setPassword(password); // 设置新密码
        }
        if (!phone.isEmpty()) {
            user.setPhoneNumber(phone); // 设置新手机号
        }
        userRepository.save(user); // 保存用户安全信息
        return 0; // 返回成功
    }
}
