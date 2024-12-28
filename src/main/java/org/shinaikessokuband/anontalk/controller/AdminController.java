package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.service.MessageService;
import org.shinaikessokuband.anontalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/api/users")
    public Response<String> adminPage(Model model) {
        model.addAttribute("getAllUsers", userService.getAllUsers());
        model.addAttribute("getOnlineUserCount", userService.getOnlineUserCount());
        return Response.newSuccess("api/users"); // 返回管理页面视图
    }

    @PutMapping("/api/users/{userId}")
    public Response<String> disableUser(@PathVariable String userId) {
        userService.banUser(userId);
        return Response.newSuccess("redirect:/api/users");
    }

    @PostMapping("/api/users/{userId}")
    public Response<String> activateUser(@PathVariable String userId) {
        userService.activateUser(userId);
        return Response.newSuccess("redirect:/api/users");
    }

    @GetMapping("/admin/exportChatHistory")
    public String exportChatHistory() {
        try {
            messageService.exportChatHistory("chat_history.json"); // 导出聊天记录
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }
}
