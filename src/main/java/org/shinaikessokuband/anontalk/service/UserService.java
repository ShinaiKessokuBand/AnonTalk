package org.shinaikessokuband.anontalk.service;

import org.shinaikessokuband.anontalk.dto.UserRegDto;
import org.shinaikessokuband.anontalk.entity.User;
import org.shinaikessokuband.anontalk.dto.UserDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 用户服务接口，定义用户相关的服务方法。
 */
public interface UserService {

    /**
     * 根据用户名获取用户信息。
     *
     * @param userName 用户名
     * @return 用户数据传输对象（UserDto）
     */
    UserDto getUserByAccount(String userName);

    /**
     * 根据用户名删除用户账户。
     *
     * @param userName 用户名
     */
    void deleteUserByAccount(String userName);

    /**
     * 用户登录。
     *
     * @param username 用户名
     * @param password 用户密码
     * @return 用户 ID，如果登录失败返回 -1
     */
    int login(String username,
              String password);

    /**
     * 用户登出。
     *
     * @param userName 用户名
     * @return 如果登出成功返回 true，否则返回 false
     */
    boolean logout(String userName);

    /**
     * 注册新用户，事务性操作。
     *
     * @param phone    用户手机号
     * @param username 用户名
     * @param password 用户密码
     * @return 新用户 ID
     */
    @Transactional
    Integer registerNewUser(String phone,
                            String username,
                            String password);

    /**
     * 获取所有用户列表。
     *
     * @return 用户列表
     */
    List<User> getAllUsers();

    /**
     * 获取在线用户的数量。
     *
     * @return 在线用户数量
     */
    int getOnlineUserCount();

    /**
     * 封禁用户。
     *
     * @param userName 用户名
     */
    void banUser(String userName);

    /**
     * 解封用户。
     *
     * @param userName 用户名
     */
    void activateUser(String userName);

    /**
     * 更新用户信息。
     *
     * @param userid   用户 ID
     * @param username 新用户名
     * @param gender   性别
     * @param hobbies  爱好
     * @return 0 表示成功，-1 表示用户不存在
     */
    int updateUserInfo(int userid,
                       String username,
                       String gender,
                       String hobbies);

    /**
     * 更新用户安全信息。
     *
     * @param userid   用户 ID
     * @param password 新密码
     * @param phone    新手机号
     * @return 0 表示成功，-1 表示用户不存在
     */
    int updateUserSecurity(
            int userid,
            String password,
            String phone);
}

