package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.dao.User;
import org.shinaikessokuband.anontalk.service.SettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
public class SettingController {
    @Autowired
    SettingService settingService;

    @PutMapping("/setting/update")
    public Response<String> updateUserByAccount(@PathVariable String account, @RequestParam(required = false) String name,
                                              @RequestParam(required = false) String email, @RequestParam(required = false) String password,
                                              @RequestParam(required = false) String hobbies, @RequestParam(required = false) String phoneNumber,
                                              Model model) {
        model.addAttribute("update", settingService.updateStudentByAccount(account,name,email,password,hobbies,phoneNumber));
        return Response.newSuccess("setting");
    }
    @GetMapping("/setting")
    public Response<User> getUserByAccount(@PathVariable String account, Model model) {
        model.addAttribute("account", settingService.getUserByAccount(account));
        return Response.newSuccess(settingService.getUserByAccount(account));
    }
}
