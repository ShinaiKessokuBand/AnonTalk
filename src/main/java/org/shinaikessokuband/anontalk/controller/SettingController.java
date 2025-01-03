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
    @GetMapping("/api/users")
    public Response<User> getUserByAccount(@PathVariable String userName, Model model) {
        model.addAttribute("account", settingService.getUserByAccount(userName));
        return Response.newSuccess(settingService.getUserByAccount(userName));
    }
}
