package com.egrand.provider.ram.api.service.impl;

import com.egrand.provider.ram.api.service.RoleService;
import com.egrand.commons.base.model.RestResponse;
import com.egrand.provider.ram.api.model.vo.RoleVo;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {
    @Override
    public RestResponse<List<RoleVo>> getRoleByUserId(Integer userId) {
        return RestResponse.failed("100","调用getRoleByUserId失败");
    }
}
