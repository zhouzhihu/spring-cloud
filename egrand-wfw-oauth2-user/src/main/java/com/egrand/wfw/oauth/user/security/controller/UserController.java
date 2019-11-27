package com.egrand.wfw.oauth.user.security.controller;

import com.egrand.wfw.oauth.user.security.model.User;
import com.egrand.wfw.oauth.user.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

}