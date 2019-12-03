package com.egrand.wfw.oauth.security.service.impl;

import com.egrand.commons.lang.model.ApiResponse;
import com.egrand.wfw.oauth.api.vo.UserVo;
import com.egrand.wfw.oauth.security.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Override
    public ApiResponse<UserVo> findByUsername(String username) {
        return ApiResponse.failed("100","调用findByUsername接口失败");
    }

    @Override
    public ApiResponse<UserVo> findByUserId(Long userId) {
        return ApiResponse.failed("100","调用findByUserId接口失败");
    }
}
