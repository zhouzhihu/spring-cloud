package com.egrand.wfw.ram.controller;

import com.egrand.commons.lang.model.ApiResponse;
import com.egrand.wfw.ram.entity.User;
import com.egrand.wfw.ram.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户
     */
    @GetMapping("/{username}")
    public ApiResponse findByUsername(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        if (user == null){
            return ApiResponse.failed("","");
        }
        return ApiResponse.success(user);
    }
}
