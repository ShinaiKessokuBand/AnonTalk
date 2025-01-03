package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin")
public class AdminController {

    private final UserService userService;

    public AdminController(UserService userService) {
        this.userService = userService;
    }

    /*
    函数名：disableUser
    作用：删除用户，达到无法登陆的效果，即将用户状态设置为禁用
     */
    @DeleteMapping("/api/users/{userId}")
    public String disableUser(@PathVariable String userId) {
        userService.banUser(userId);
        return "User disabled successfully";
    }


}