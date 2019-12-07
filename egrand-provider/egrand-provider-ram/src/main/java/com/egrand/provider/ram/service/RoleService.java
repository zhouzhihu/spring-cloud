package com.egrand.provider.ram.service;

import com.egrand.provider.ram.dao.RoleDao;
import com.egrand.provider.ram.model.domain.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    @Autowired
    private RoleDao roleDao;

    /**
     * 根据用户名查找用户
     * @param userId 用户ID
     * @return 用户
     */
    public List<Role> findByUserId(Long userId){
        List<Object[]> roles = this.roleDao.getRoleByUserId(userId);
        List<Role> newRoles = new ArrayList<Role>();
        for(Object[] role:roles){
            Role tmp = new Role();
            tmp.setId(((BigInteger)role[0]).longValue());
            tmp.setRoleName((String)role[1]);
            tmp.setRoleCode((String)role[2]);
            newRoles.add(tmp);
        }
        return newRoles;
    }
}
