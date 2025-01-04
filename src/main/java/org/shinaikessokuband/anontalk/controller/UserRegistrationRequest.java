package org.shinaikessokuband.anontalk.controller;

import lombok.Getter;
import lombok.Setter;


public class UserRegistrationRequest {
    // Getters and setters
    private String phone;
    private String username;
    private String password;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}