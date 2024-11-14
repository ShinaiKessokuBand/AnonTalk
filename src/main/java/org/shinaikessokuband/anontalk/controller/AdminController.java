package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.service.MessageService;
import org.shinaikessokuband.anontalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;
    @Autowired
    private MessageService messageService;

    @GetMapping("/admin")
    public Response<String> adminPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("onlineUserCount", userService.getOnlineUserCount());
        return Response.newSuccess("admin"); // 返回管理页面视图
    }

    @PostMapping("/admin/banUser")
    public Response<String> banUser(@RequestParam String account) {
        userService.banUser(account);
        return Response.newSuccess("redirect:/admin");
    }

    @PostMapping("/admin/activateUser")
    public Response<String> activateUser(@RequestParam String account) {
        userService.activateUser(account);
        return Response.newSuccess("redirect:/admin");
    }

    @PostMapping("/admin/exportChatHistory")
    public String exportChatHistory() {
        try {
            messageService.exportChatHistory("chat_history.json"); // 导出聊天记录
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "redirect:/admin";
    }
}
