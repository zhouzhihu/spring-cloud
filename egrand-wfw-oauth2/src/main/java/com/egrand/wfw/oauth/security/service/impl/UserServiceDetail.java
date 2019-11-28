package com.egrand.wfw.oauth.security.service.impl;

import com.egrand.wfw.oauth.api.util.StatusCode;
import com.egrand.wfw.oauth.api.vo.Result;
import com.egrand.wfw.oauth.api.vo.RoleVo;
import com.egrand.wfw.oauth.api.vo.UserVo;
import com.egrand.wfw.oauth.security.service.RoleService;
import com.egrand.wfw.oauth.security.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class UserServiceDetail implements UserDetailsService {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("======查找用户======");
        System.out.println("userService = " + userService);
        Result<UserVo> userResult = userService.findByUsername(username);
        System.out.println("code = " + userResult.getCode());
        System.out.println("msg = " + userResult.getMsg());
        if (userResult.getCode() != StatusCode.SUCCESS_CODE) {
            throw new UsernameNotFoundException("用户:" + username + ",不存在!");
        }
        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        boolean enabled = true; // 可用性 :true:可用 false:不可用
        boolean accountNonExpired = true; // 过期性 :true:没过期 false:过期
        boolean credentialsNonExpired = true; // 有效性 :true:凭证有效 false:凭证无效
        boolean accountNonLocked = true; // 锁定性 :true:未锁定 false:已锁定
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userResult.getData(),userVo);
        System.out.println("=============");
        System.out.println("userVo = " + userVo.toString());
        System.out.println("=============");
        Result<List<RoleVo>> roleResult = roleService.getRoleByUserId(userVo.getId());
        System.out.println("======查找用户(" + userVo.getId() + ")角色======");
        System.out.println("code = " + roleResult.getCode());
        System.out.println("msg = " + roleResult.getMsg());
        System.out.println("roleResult = " + roleResult.toString());
        System.out.println("size = " + roleResult.getData().size());
        if (roleResult.getCode() == StatusCode.SUCCESS_CODE) {
            List<RoleVo> roleVoList = roleResult.getData();
            for (RoleVo role : roleVoList) {
                System.out.println("name = " + role.getName());
                GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
                grantedAuthorities.add(grantedAuthority);
            }
        }
        System.out.println("author size = " + grantedAuthorities.size());
        User user = new User(userVo.getUsername(), userVo.getPassword(),
                enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, grantedAuthorities);
        return user;
    }
}
