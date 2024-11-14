package org.shinaikessokuband.anontalk.converter;

import org.shinaikessokuband.anontalk.dao.User;
import org.shinaikessokuband.anontalk.dto.UserDto;

public class UserConverter {
    public static UserDto convertUser(User user) {
        UserDto userDto = new UserDto();
        userDto.setAccount(user.getAccount());
        userDto.setName(user.getName());
        userDto.setEmail(user.getEmail());
        userDto.setPassword(user.getPassword());
        userDto.setPhoneNumber(user.getPhoneNumber());
        userDto.setOnline(user.isOnline());
        return userDto;
    }
    public static User convertUserDto(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(userDto.getPassword());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setOnline(userDto.isOnline());
        return user;
    }
}
