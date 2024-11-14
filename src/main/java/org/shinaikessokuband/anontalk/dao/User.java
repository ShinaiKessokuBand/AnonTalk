package org.shinaikessokuband.anontalk.dao;

import jakarta.persistence.*;
import lombok.Data;

import static jakarta.persistence.GenerationType.IDENTITY;

@Data
@Entity
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "username")//
    private String account;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "age")
    private String age;

    @Column(name = "gender")
    private String gender;

    @Column(name = "hobbies")
    private String hobbies;

    @Column(name = "phonenumber")
    private String phoneNumber;

    @Column(name = "")
    private boolean isOnline = false;//DEFAULT FALSE

    @Column(name = "")
    private boolean isBanned = false;//DEFAULT FALSE
}
