package com.egrand.wfw.oauth.user.security.controller;

import com.egrand.wfw.oauth.api.vo.Result;
import com.egrand.wfw.oauth.user.security.model.User;
import com.egrand.wfw.oauth.user.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/registry",method = RequestMethod.POST)
    public User createUser(@RequestParam("username") String username
            , @RequestParam("password") String password) {
        return userService.create(username,password);
    }

    @GetMapping("findByUsername/{username}")
    public Result findByUsername(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        if (user == null){
            return Result.failure(100,"用户不存在");
        }
        return Result.ok().setData(user);
    }

}