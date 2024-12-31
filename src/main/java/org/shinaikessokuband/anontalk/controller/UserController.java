package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.dto.UserDto;
import org.shinaikessokuband.anontalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.shinaikessokuband.anontalk.dto.UserRegDto;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8089")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userName}")
    public Response<UserDto> getUserByAccount(@PathVariable String userName) {
        return Response.newSuccess(userService.getUserByAccount(userName));
    }

    @CrossOrigin(origins = "http://localhost:8089")
    @PostMapping("/register")
    public Response<String> registerNewUser(@RequestBody UserRegDto userDto) {
        return Response.newSuccess(userService.registerNewUser(userDto).toString());
    }

    @DeleteMapping("/users/{userName}")
    public void deleteUser(@PathVariable String userName) {
        userService.deleteUserByAccount(userName);
    }
//,@RequestParam(required = false) String email,@RequestParam(required = false) String phoneNumber
    @PostMapping("/login")
    public Response<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        int userid = userService.login(username, password);
        Map<String, Object> response = new HashMap<>();
        if(userid == -1) {
            response.put("success", false);
            return Response.newSuccess(response);
        }
        response.put("success", true);
        response.put("userid", userid);
        return Response.newSuccess(response);
    }

    @PutMapping("/logout")
    public Response<String> logout(String userName) {
        if (userService.logout(userName))
            return Response.newSuccess("Logout successful!");
        else
            return Response.newSuccess("Logout failed! Not online now.");
    }
}
