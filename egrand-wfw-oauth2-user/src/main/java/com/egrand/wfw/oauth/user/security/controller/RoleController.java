package com.egrand.wfw.oauth.user.security.controller;

import com.egrand.wfw.oauth.api.vo.Result;
import com.egrand.wfw.oauth.user.security.model.Role;
import com.egrand.wfw.oauth.user.security.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-06-13
 * Time: 10:27
 */
@RestController
@RequestMapping("role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @GetMapping("getRoleByUserId/{userId}")
    public Result getRoleByUserId(@PathVariable("userId") Integer userId){
        List<Role> roleList = roleService.getRoleByUserId(userId);
        return Result.ok().setData(roleList);
    }

}
