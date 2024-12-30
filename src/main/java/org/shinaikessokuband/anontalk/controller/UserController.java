package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.dto.UserDto;
import org.shinaikessokuband.anontalk.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:8089")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/api/users/{userName}")
    public Response<UserDto> getUserByAccount(@PathVariable String userName) {
        return Response.newSuccess(userService.getUserByAccount(userName));
    }

    @CrossOrigin(origins = "http://localhost:8089")
    @PostMapping("/api/register")
    public Response<String> registerNewUser(@RequestBody UserDto userDto) {
        return Response.newSuccess(userService.registerNewUser(userDto).toString());
    }

    @DeleteMapping("/api/users/{userName}")
    public void deleteUser(@PathVariable String userName) {
        userService.deleteUserByAccount(userName);
    }
//,@RequestParam(required = false) String email,@RequestParam(required = false) String phoneNumber
    @PutMapping("/api/login")
    public Response<String> login(@RequestParam String userName, @RequestParam String password) {
        UserDto userDto = userService.login(userName,password);
        if (userDto != null) {

            return Response.newSuccess("Login successful!");
        }else{
            return Response.newSuccess("Invalid account or password!");
        }
    }
    @GetMapping("/api/login")
    public ModelAndView getLogin() {
        return new ModelAndView("redirect:/index.html");
    }
    @PutMapping("/api/logout")
    public Response<String> logout(String userName) {
        if (userService.logout(userName))
            return Response.newSuccess("Logout successful!");
        else
            return Response.newSuccess("Logout failed! Not online now.");
    }
}
