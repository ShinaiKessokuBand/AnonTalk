package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    @Autowired
    private UserService userService;

    @DeleteMapping("/api/users/{userId}")
    public String disableUser(@PathVariable String userId) {
        userService.banUser(userId);
        return "User disabled successfully";
    }
}