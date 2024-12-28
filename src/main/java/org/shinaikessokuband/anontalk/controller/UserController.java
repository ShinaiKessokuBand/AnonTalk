package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.dto.UserDto;
import org.shinaikessokuband.anontalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{account}")
    public Response<UserDto> getUserByAccount(@PathVariable String account) {
        return Response.newSuccess(userService.getUserByAccount(account));
    }

    @PostMapping("/api/register")
    public Response<String> createUser(@RequestBody String phone,@RequestBody String password,@RequestBody String username) {
        return Response.newSuccess(userService.registerNewUser(phone,password,username));
    }

    @DeleteMapping("/user/{account}")
    public void deleteUser(@PathVariable String account) {
        userService.deleteUserByAccount(account);
    }
//,@RequestParam(required = false) String email,@RequestParam(required = false) String phoneNumber
    @PostMapping("/api/login")
    public Response<String> loginUser(@RequestParam String username, @RequestParam String password) {
        UserDto userDto = userService.login(username,password);
        if (userDto != null) {
            return Response.newSuccess("Login successful!");
        }else{
            return Response.newSuccess("Invalid account or password!");
        }
    }
    @PostMapping("/logout")
    public Response<String> logout(String account) {
        if (userService.logout(account))
            return Response.newSuccess("Logout successful!");
        else
            return Response.newSuccess("Logout failed! Not online now.");
    }
}
