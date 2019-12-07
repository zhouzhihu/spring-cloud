package com.egrand.provider.ram.api.service.impl;

import com.egrand.commons.base.model.RestResponse;
import com.egrand.provider.ram.api.service.UserService;
import com.egrand.provider.ram.api.model.vo.UserVo;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Override
    public RestResponse<UserVo> findByUsername(String username) {
        return RestResponse.failed("100","调用findByUsername接口失败");
    }

    @Override
    public RestResponse<UserVo> findByUserId(Long userId) {
        return RestResponse.failed("100","调用findByUserId接口失败");
    }
}
