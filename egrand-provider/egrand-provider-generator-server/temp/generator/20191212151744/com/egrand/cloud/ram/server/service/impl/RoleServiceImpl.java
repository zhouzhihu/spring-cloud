package com.egrand.cloud.ram.server.service.impl;

import com.egrand.cloud.ram.client.model.entity.Role;
import com.egrand.cloud.ram.server.mapper.RoleMapper;
import com.egrand.cloud.ram.server.service.RoleService;
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
public class RoleServiceImpl extends BaseServiceImpl<RoleMapper, Role> implements RoleService {

}
