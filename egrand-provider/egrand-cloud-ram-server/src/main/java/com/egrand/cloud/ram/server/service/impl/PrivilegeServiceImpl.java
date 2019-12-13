package com.egrand.cloud.ram.server.service.impl;

import com.egrand.cloud.ram.client.model.entity.Privilege;
import com.egrand.cloud.ram.server.mapper.PrivilegeMapper;
import com.egrand.cloud.ram.server.mapper.RolePrivilegeMapper;
import com.egrand.cloud.ram.server.service.PrivilegeService;
import com.egrand.cloud.ram.server.service.RolePrivilegeService;
import com.egrand.core.mybatis.base.service.impl.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *  服务实现类
 *
 * @author ZZH
 * @date 2019-12-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PrivilegeServiceImpl extends BaseServiceImpl<PrivilegeMapper, Privilege> implements PrivilegeService {

    @Autowired
    private RolePrivilegeMapper rolePrivilegeMapper;

    @Override
    public List<Privilege> getRolePrivileges(Long roleId) {
        return this.rolePrivilegeMapper.selectRolePrivilegesList(roleId);
    }
}
