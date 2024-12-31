package org.shinaikessokuband.anontalk.entity;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Table(name = "user")
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "userId")//account=username
    private Integer userId;

    @Column(name = "password")
    private String password;

    @Column(name = "username")
    private String username;//用户名

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private int age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "hobbies")
    private String hobbies;

    @Column(name = "phonen")
    private String phoneNumber;

    @Column(name = "")
    private boolean isOnline = false;//DEFAULT FALSE

    @Column(name = "")
    private boolean isBanned = false;//DEFAULT FALSE


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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getHobbies() {
        return hobbies;
    }

    public void setHobbies(String hobbies) {
        this.hobbies = hobbies;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phone) {
        this.phoneNumber = phone;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
