package com.egrand.wfw.oauth.security.service.impl;

import com.egrand.commons.lang.model.ApiResponse;
import com.egrand.wfw.oauth.api.vo.RoleVo;
import com.egrand.wfw.oauth.security.service.RoleService;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public class RoleServiceImpl implements RoleService {
    @Override
    public ApiResponse<List<RoleVo>> getRoleByUserId(Integer userId) {
        return ApiResponse.failed("100","调用getRoleByUserId失败");
    }
}
