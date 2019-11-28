package com.egrand.wfw.oauth.user.security.service;

import com.egrand.wfw.oauth.user.security.dao.RoleDao;
import com.egrand.wfw.oauth.user.security.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleDao roleDao;

    public List<Role> getRoleByUserId(Integer userId){
        List<Object[]> roles = this.roleDao.getRoleByUserId(userId);
        List<Role> newRoles = new ArrayList<Role>();
        for(Object[] role:roles){
            newRoles.add(new Role(((BigInteger)role[0]).longValue(), (String)role[1]));
        }
        return newRoles;
    }
}
