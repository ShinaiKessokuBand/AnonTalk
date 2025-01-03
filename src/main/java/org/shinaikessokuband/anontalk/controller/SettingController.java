package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.entity.User;
import org.shinaikessokuband.anontalk.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class SettingController {
    final
    SettingService settingService;

    public SettingController(SettingService settingService) {
        this.settingService = settingService;
    }

    /*@PutMapping("/api/users/{userId}")
    public Response<String> updateUser(@PathVariable Integer userId, @RequestParam(required = false) String username,
                                              @RequestParam(required = false) String email, @RequestParam(required = false) String password,
                                              @RequestParam(required = false) String hobbies, @RequestParam(required = false) String phoneNumber,
                                              Model model) {
        model.addAttribute("updateUser", settingService.updateStudentByAccount(userId,username,email,password,hobbies,phoneNumber));
        return Response.newSuccess("/api/users");
    }*/

    /**
     * 根据用户名获取用户信息。
     *
     * 该方法通过用户名（`userName`）查询用户信息，返回一个包含用户详情的响应。
     * 同时，它还会将查询到的用户信息放入模型对象中，以便用于前端页面渲染。
     *
     * 请求的 URL：/api/users/{userName}
     * 请求方法：GET
     *
     * @param userName 用户的账户名，通过路径参数传入
     * @param model Spring MVC 的模型对象，用于传递数据到前端页面
     * @return 返回一个包含用户信息的成功响应
     */
    @GetMapping("/api/users")
    public Response<User> getUserByAccount(@PathVariable String userName, Model model) {
        model.addAttribute("account", settingService.getUserByAccount(userName));
        return Response.newSuccess(settingService.getUserByAccount(userName));
    }
}
