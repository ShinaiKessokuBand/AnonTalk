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
@CrossOrigin(origins = "${app.domain}")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/users/{userName}")
    public Response<UserDto> getUserByAccount(@PathVariable String userName) {
        return Response.newSuccess(userService.getUserByAccount(userName));
    }

    @CrossOrigin(origins = "${app.domain}")
    @PostMapping("/register")
    public Response<String> registerNewUser(@RequestBody Map<String, String> registerData) {
        String phone = registerData.get("phone");
        String username = registerData.get("username");
        String password = registerData.get("password");
        Map<String, Object> response = new HashMap<>();
        int ret = userService.registerNewUser(phone, username, password);
        if(ret == -1) {
            response.put("success", false);
            return Response.newSuccess(response.toString());
        }
        else {
            response.put("success", true);
            response.put("userid", ret);
            return Response.newSuccess(response.toString());
        }
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
            return Response.newError(response);
        }
        response.put("success", true);
        response.put("userid", userid);
        return Response.newSuccess(response);
    }


    @PutMapping("/users/{userid}")
    public Response<Map<String, Object>> updateUser(@PathVariable int userid, @RequestBody Map<String, String> updateData) {
        String username = updateData.get("username");
        String gender = updateData.get("gender");
        String hobbies = updateData.get("hobbies");
        Map<String, Object> response = new HashMap<>();
        int ret = userService.updateUserInfo(userid, username, gender, hobbies);
        if(ret == -1) {
            response.put("success", false);
            return Response.newSuccess(response);
        }
        else {
            response.put("success", true);
            response.put("userid", ret);
            return Response.newSuccess(response);
        }
    }

    @PutMapping("/userSecurity/{userid}")
    public Response<Map<String, Object>> updateUserSecurity(@PathVariable int userid, @RequestBody Map<String, String> updateData) {
        String password = updateData.get("password");
        String phone = updateData.get("phone");
        Map<String, Object> response = new HashMap<>();
        int ret = userService.updateUserSecurity(userid, password, phone);
        if(ret == -1) {
            response.put("success", false);
            return Response.newSuccess(response);
        }
        else {
            response.put("success", true);
            response.put("userid", ret);
            return Response.newSuccess(response);
        }
    }
    @PutMapping("/logout")
    public Response<String> logout(String userName) {
        if (userService.logout(userName))
            return Response.newSuccess("Logout successful!");
        else
            return Response.newSuccess("Logout failed! Not online now.");
    }
}