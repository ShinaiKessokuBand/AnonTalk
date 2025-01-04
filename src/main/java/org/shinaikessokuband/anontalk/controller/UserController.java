package org.shinaikessokuband.anontalk.controller;

import org.shinaikessokuband.anontalk.Response;
import org.shinaikessokuband.anontalk.dto.UserDto;
import org.shinaikessokuband.anontalk.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "${app.domain}")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /*
    函数名：    getUserByAccount
    参数列表：  String userName  请求查询的用户名
     */
    @GetMapping("/users/{userName}")
    public Response<UserDto> getUserByAccount(@PathVariable String userName) {
        return Response.newSuccess(userService.getUserByAccount(userName));
    }
    /*
    函数名：    registerNewUser
    参数列表：  @RequestBody Map<String, String> registerData  注册信息体
                    - phone:    String
                    - username: String
                    - password: String
    返回值：    Response<String>  注册结果，Response的格式，将自动被序列化，前端直接用 JSON 格式读取即可
                    - success: boolean  注册是否成功
                    - userid:  int      注册成功后的用户ID
     */
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
            return Response.newError(response.toString());
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

    /*
    函数名：    login
    参数列表：  @RequestBody Map<String, String> loginData  登录信息体
                    - username: String
                    - password: String
    返回值：    Response<Map<String, Object>>  登录结果，Response的格式，将自动被序列化，前端直接用 JSON 格式读取即可
                    - success: boolean  登录是否成功
                    - userid:  int      登录成功的用户ID
     */
    @PostMapping("/login")
    public Response<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String username = loginData.get("username");
        String password = loginData.get("password");
        int userid = userService.login(username, password);
        Map<String, Object> response = new HashMap<>();
        if(userid == -1) {
            response.put(
                    "success",
                    false
            );
            return Response.newError(response);
        }
        response.put(
                "success",
                true
        );
        response.put(
                "userid",
                userid
        );
        return Response.newSuccess(response);
    }

    /*
    函数名：    updateUser
    参数列表：  int userid      请求更改信息的用户ID
               @RequestBody Map<String, String> updateData  更改后的信息体
                    - username: String
                    - gender:   String
                    - hobbies:  String
    返回值：    Response<Map<String, Object>>  更改结果，Response的格式，将自动被序列化，前端直接用 JSON 格式读取即可
     */
    @PutMapping("/users/{userid}")
    public Response<Map<String, Object>> updateUser(@PathVariable int userid,
                                                    @RequestBody Map<String, String> updateData) {
        String username = updateData.get("username");
        String gender = updateData.get("gender");
        String hobbies = updateData.get("hobbies");
        Map<String, Object> response = new HashMap<>();
        int ret = userService.updateUserInfo(
                userid,
                username,
                gender,
                hobbies
        );
        if(ret == -1) {
            response.put(
                    "success",
                    false
            );
            return Response.newError(response);
        }
        else {
            response.put(
                    "success",
                    true
            );
            response.put(
                    "userid",
                    ret
            );
            return Response.newSuccess(response);
        }
    }

    /*
    函数名：    updateUserSecurity
    参数列表：  int userid      请求更改信息的用户ID
               @RequestBody Map<String, String> updateData  更改后的信息体
                    - password: String
                    - phone:    String
    返回值：    Response<Map<String, Object>>  更改结果，Response的格式，将自动被序列化，前端直接用 JSON 格式读取即可
     */
    @PutMapping("/userSecurity/{userid}")
    public Response<Map<String, Object>> updateUserSecurity(@PathVariable int userid,
                                                            @RequestBody Map<String, String> updateData) {
        String password = updateData.get("password");
        String phone = updateData.get("phone");
        Map<String, Object> response = new HashMap<>();
        int ret = userService.updateUserSecurity(userid, password, phone);
        if(ret == -1) {
            response.put("success", false);
            return Response.newError(response);
        }
        else {
            response.put("success", true);
            response.put("userid", ret);
            return Response.newSuccess(response);
        }
    }

    /*
    函数名：    logout
    参数列表：  String userName  请求登出的用户名
    返回值：    Response<String>  登出结果，Response的格式，将自动被序列化，前端直接用 JSON 格式读取即可
     */
    @PutMapping("/logout")
    public Response<String> logout(String userName) {
        if (userService.logout(userName))
            return Response.newSuccess("Logout successful!");
        else
            return Response.newError("Logout failed! Not online now.");
    }
}