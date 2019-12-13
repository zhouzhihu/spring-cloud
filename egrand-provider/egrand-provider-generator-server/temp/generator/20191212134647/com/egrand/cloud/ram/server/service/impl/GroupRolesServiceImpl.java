package com.egrand.cloud.ram.server.service.impl;

import com.egrand.cloud.ram.client.model.entity.GroupRoles;
import com.egrand.cloud.ram.server.mapper.GroupRolesMapper;
import com.egrand.cloud.ram.server.service.GroupRolesService;
import com.egrand.core.mybatis.base.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author ZZH
 * @date 2019-12-12
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class GroupRolesServiceImpl extends BaseServiceImpl<GroupRolesMapper, GroupRoles> implements GroupRolesService {

}
