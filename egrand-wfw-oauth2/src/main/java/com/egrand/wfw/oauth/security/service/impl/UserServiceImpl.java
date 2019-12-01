package com.egrand.wfw.oauth.security.service.impl;

import com.egrand.wfw.oauth.api.vo.Result;
import com.egrand.wfw.oauth.api.vo.UserVo;
import com.egrand.wfw.oauth.security.service.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {
    @Override
    public Result<UserVo> findByUsername(String username) {
        return Result.failure(100,"调用findByUsername接口失败");
    }

    @Override
    public Result<UserVo> findByUserId(Long userId) {
        return Result.failure(100,"调用findByUserId接口失败");
    }
}
