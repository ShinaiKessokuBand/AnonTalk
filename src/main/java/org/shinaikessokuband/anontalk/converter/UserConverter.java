package org.shinaikessokuband.anontalk.converter;

import org.shinaikessokuband.anontalk.dto.UserRegDto;
import org.shinaikessokuband.anontalk.entity.User;
import org.shinaikessokuband.anontalk.dto.UserDto;

public class UserConverter {
    public static UserDto convertUser(User user) {
        UserDto userDto = new UserDto();
    //    userDto.setAccount(user.getAccount());
        userDto.setUsername(user.getUsername());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setOnline(user.isOnline());
        return userDto;
    }
    public static User convertUserDto(UserDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setOnline(userDto.isOnline());
        return user;
    }
    public static User convertUserDto(UserRegDto userDto) {
        User user = new User();
        user.setUsername(userDto.getUsername());
        user.setEmail("");
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setOnline(false);
        return user;
    }
}
