package com.egrand.provider.ram.controller;

import com.egrand.commons.base.model.RestResponse;
import com.egrand.commons.base.model.RestReturnCodeEnum;
import com.egrand.provider.ram.model.domain.User;
import com.egrand.provider.ram.service.UserService;
import com.egrand.provider.ram.utils.BPwdEncoderUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/getPrinciple")
    public OAuth2Authentication getPrinciple(OAuth2Authentication oAuth2Authentication, Principal principal,
                                             Authentication authentication){
        System.out.println(oAuth2Authentication.getUserAuthentication().getAuthorities().toString());
        System.out.println(oAuth2Authentication.toString());
        System.out.println("principal.toString()"+principal.toString());
        System.out.println("principal.getName()"+principal.getName());
        System.out.println("authentication:"+authentication.getAuthorities().toString());
        return oAuth2Authentication;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/test")
    @ApiOperation(value = "测试权限")
    public void test(){
        System.out.println("test role admin");
    }

    @PutMapping("/")
    @ApiOperation(value = "添加用户")
    public RestResponse<User> save(@RequestBody User user){
        if (user == null){
            return RestResponse.failed(RestReturnCodeEnum.NO_CONTENT);
        }
        user.setPassword(BPwdEncoderUtil.BCryptPassword(user.getPassword()));
        user = this.userService.save(user);
        return RestResponse.success(user);
    }

    @PostMapping("/")
    @ApiOperation(value = "修改用户")
    public RestResponse<User> update(@RequestBody User user){
        if (user == null){
            return RestResponse.failed(RestReturnCodeEnum.NO_CONTENT);
        }
        user = this.userService.save(user);
        return RestResponse.success(user);
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "根据ID删除用户")
    public RestResponse delete(@PathVariable("id") Long id){
        if (id == -1){
            return RestResponse.failed("","");
        }
        this.userService.delete(id);
        return RestResponse.success();
    }

    @GetMapping("/query/username/{username}")
    @ApiOperation(value = "根据用户名查找用户")
    public RestResponse<User> findByUsername(@PathVariable("username") String username){
        System.out.println("username = " + username);
        User user = userService.findByUsername(username);
        if (user == null){
            return RestResponse.failed("001","查找用户为空！");
        }
        return RestResponse.success(user);
    }

    @GetMapping("/query/userId/{userId}")
    @ApiOperation(value = "根据ID查找用户")
    public RestResponse<User> findByUserId(@PathVariable("userId") Long userId){
        User user = userService.findById(userId);
        if (user == null){
            return RestResponse.failed("","");
        }
        return RestResponse.success(user);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "根据ID查找用户")
    public RestResponse<User> findById(@PathVariable("id") Long id){
        User user = userService.findById(id);
        if (user == null){
            return RestResponse.failed("","");
        }
        return RestResponse.success(user);
    }
}
