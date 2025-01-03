package org.shinaikessokuband.anontalk.dto;

public class UserDto {
    private Integer userId;
    private String password;
    private String confirmPassword;
    private String username;
    private String email;
    private String phoneNumber;
    private boolean isOnline;

    // Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    // toString method
    @Override
    public String toString() {
        return "UserDto{" +
                "userId=" + userId +
                ", password='" + password + '\'' +
                ", confirmPassword='" + confirmPassword + '\'' +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", isOnline=" + isOnline +
                '}';
    }

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDto userDto = (UserDto) o;

        if (isOnline != userDto.isOnline) return false;
        if (!userId.equals(userDto.userId)) return false;
        if (!password.equals(userDto.password)) return false;
        if (!confirmPassword.equals(userDto.confirmPassword)) return false;
        if (!username.equals(userDto.username)) return false;
        if (!email.equals(userDto.email)) return false;
        return phoneNumber.equals(userDto.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = userId.hashCode();
        result = 31 * result + password.hashCode();
        result = 31 * result + confirmPassword.hashCode();
        result = 31 * result + username.hashCode();
        result = 31 * result + email.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + (isOnline ? 1 : 0);
        return result;
    }
}
