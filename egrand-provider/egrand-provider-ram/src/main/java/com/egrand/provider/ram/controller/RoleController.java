package com.egrand.provider.ram.controller;

import com.egrand.commons.base.model.RestResponse;
import com.egrand.provider.ram.model.domain.Role;
import com.egrand.provider.ram.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/query/userId/{userId}")
    public RestResponse getRoleByUserId(@PathVariable("userId") Long userId){
        List<Role> roleList = roleService.findByUserId(userId);
        return RestResponse.success(roleList);
    }
}
