package com.xb.controller;

import com.xb.entity.Page;
import com.xb.entity.User;
import com.xb.service.UserService;
import com.xb.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @author xb
 * @description 用户Controller
 * @create 2023-07-03 11:41
 * @vesion 1.0
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/all")
    public List<User> selectList() {
        return userService.selectList();
    }


    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody User user) {
        Map<String, Object> data = userService.login(user);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail("用户名或密码错误", null);
    }

    @GetMapping("/info")
    public Result<Map<String, Object>> info(@RequestParam String token) {
        Map<String, Object> data = userService.getInfoByToken(token);
        if (data != null) {
            return Result.success(data);
        }
        return Result.fail("账号已失效，请重新登录", null);
    }

    @PostMapping("/logout")
    public Result<Map<String, Object>> logOut(@RequestHeader("X-Token") String token) {
        userService.logOut(token);
        return Result.success(null);
    }

    @PostMapping("/page")
    public Result<Map<String, Object>> page(@RequestBody Page page) {
        Map<String, Object> data = userService.queryUsersBySql(page);
        return Result.success(data);
    }

    @PostMapping
    public Result<Map<String, Object>> saveUser(@RequestBody User user){
        userService.saveUser(user);
        return new Result<>(20000,"新增用户成功",null);
    }

    @GetMapping("/{id}")
    public Result<User> getUserById(@PathVariable("id") Integer id){
        User user = userService.getUserById(id);
        return Result.success(user);
    }

    @DeleteMapping("/{id}")
    public Result<Map<String, Object>> deleteById(@PathVariable("id") Integer id){
        userService.removeById(id);
        return new Result<>(20000,"删除用户成功",null);
    }

    @PutMapping
    public Result<Map<String, Object>> updateUser(@RequestBody User user){
        userService.update(user);
        return new Result<>(20000,"修改用户成功",null);
    }

}
