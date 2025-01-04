package org.shinaikessokuband.anontalk.converter;

import org.shinaikessokuband.anontalk.dto.UserRegDto;
import org.shinaikessokuband.anontalk.entity.User;
import org.shinaikessokuband.anontalk.dto.UserDto;

/**
 * 用户转换器类，用于在 User 实体和 User DTO 之间进行转换。
 */
public class UserConverter {

    /**
     * 将 User 实体转换为 UserDto。
     *
     * @param user 要转换的 User 实体
     * @return 转换后的 UserDto
     */
    public static UserDto convertUser(User user) {
        UserDto userDto = new UserDto();
        // 设置用户名
        userDto.setUsername(user.getUsername());
        // 设置电子邮件
        userDto.setEmail(user.getEmail());
        // 设置密码
        userDto.setPassword(user.getPassword());
        // 设置电话号码
        userDto.setPhoneNumber(user.getPhoneNumber());
        // 设置在线状态
        userDto.setOnline(user.isOnline());
        return userDto; // 返回转换后的 UserDto
    }

    /**
     * 将 UserDto 转换为 User 实体。
     *
     * @param userDto 要转换的 UserDto
     * @return 转换后的 User 实体
     */
    public static User convertUserDto(UserDto userDto) {
        User user = new User();
        // 设置用户名
        user.setUsername(userDto.getUsername());
        // 设置电子邮件
        user.setEmail(userDto.getEmail());
        // 设置密码
        user.setPassword(userDto.getPassword());
        // 设置电话号码
        user.setPhoneNumber(userDto.getPhoneNumber());
        // 设置在线状态
        user.setOnline(userDto.isOnline());
        return user; // 返回转换后的 User 实体
    }

    /**
     * 将 UserRegDto 转换为 User 实体。
     *
     * @param userDto 要转换的 UserRegDto
     * @return 转换后的 User 实体
     */
    public static User convertUserDto(UserRegDto userDto) {
        User user = new User();
        // 设置用户名
        user.setUsername(userDto.getUsername());
        // 设置电子邮件为空，因为在注册时没有提供
        user.setEmail("");
        // 设置密码
        user.setPassword(userDto.getPassword());
        // 设置电话号码
        user.setPhoneNumber(userDto.getPhoneNumber());
        // 设置在线状态为 false，表示用户初始时不在线
        user.setOnline(false);
        return user; // 返回转换后的 User 实体
    }
}

