package org.shinaikessokuband.anontalk.dto;

import lombok.Data;

@Data
public class UserDto {
    private String account;

    private String password;

    private String confirmPassword;

    private String name;

    private String email;

    private String phoneNumber;

    private boolean isOnline;
}
