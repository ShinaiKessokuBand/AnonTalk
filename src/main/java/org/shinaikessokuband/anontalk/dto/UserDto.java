package org.shinaikessokuband.anontalk.dto;

import lombok.Data;

@Data
public class UserDto {
    private Integer userId;//account=username

    private String password;

    private String confirmPassword;

    private String username;

    private String email;

    private String phoneNumber;

    private boolean isOnline;
}
