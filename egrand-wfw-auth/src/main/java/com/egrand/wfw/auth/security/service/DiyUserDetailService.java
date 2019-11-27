package com.egrand.wfw.auth.security.service;

import com.egrand.wfw.auth.api.BaseRole;
import com.egrand.wfw.auth.api.BaseUser;
import com.egrand.wfw.auth.security.model.MyUserDetail;
import com.egrand.wfw.auth.security.model.UserDetailFactory;
import com.egrand.wfw.auth.service.UserAuthService;
import com.egrand.commons.lang.BeanUtils;
import com.egrand.commons.lang.StringUtils;
import com.egrand.commons.lang.model.RestResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service("diyUserDetailService")
public class DiyUserDetailService implements UserDetailsService {
    @Autowired
    private UserAuthService userAuthService;

    public UserDetails loadUserByUsername(String identity) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(identity)) {
            throw new RuntimeException("身份不存在");
        }
        RestResponse response = this.userAuthService.getInfo(identity);
        BaseUser user = new BaseUser();
        try {
            BeanUtils.copyProperties(user, response.getData());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        BaseRole baseRole = new BaseRole();
        baseRole.setRoleName("server");
        ArrayList<BaseRole> list = new ArrayList();
        list.add(baseRole);
        MyUserDetail userDetail = UserDetailFactory.createUserDetail(user, list);
        return (UserDetails)userDetail;
    }
}
