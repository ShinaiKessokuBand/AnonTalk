package org.shinaikessokuband.anontalk.controller;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class UserRegistrationRequest {
    // Getters and setters
    private String phone;
    private String username;
    private String password;

}