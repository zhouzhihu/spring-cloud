package com.egrand.wfw.oauth.security.controller;

import com.egrand.wfw.oauth.api.vo.Result;
import com.egrand.wfw.oauth.api.vo.UserVo;
import com.egrand.wfw.oauth.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.security.Principal;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    //暴露Remote Token Services接口
    //如果其它服务需要验证Token，则需要远程调用授权服务暴露的验证Token的API接口
    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        System.out.println("principal = " + principal);
        return principal;
    }

    @RequestMapping(value = "/findByUserName", method = RequestMethod.GET)
    public Result<UserVo> getUser(@RequestParam String username){
        System.out.println("username = " + username);
        Result<UserVo> userResult = this.userService.findByUsername(username);
        System.out.println("code = " + userResult.getCode());
        System.out.println("msg = " + userResult.getMsg());
        System.out.println("data = " + userResult.getData());
        return userResult;
    }

    @RequestMapping(value = "/findByUserId", method = RequestMethod.GET)
    public Result<UserVo> getUser(@RequestParam Long userId){
        System.out.println("userId = " + userId);
        Result<UserVo> userResult = this.userService.findByUserId(userId);
        System.out.println("code = " + userResult.getCode());
        System.out.println("msg = " + userResult.getMsg());
        System.out.println("data = " + userResult.getData());
        return userResult;
    }
}
