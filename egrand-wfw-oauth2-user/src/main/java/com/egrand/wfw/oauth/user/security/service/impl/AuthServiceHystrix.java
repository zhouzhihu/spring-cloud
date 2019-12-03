package com.egrand.wfw.oauth.user.security.service.impl;

import com.egrand.wfw.oauth.user.security.model.JWT;
import com.egrand.wfw.oauth.user.security.service.AuthServiceClient;
import org.springframework.stereotype.Component;

@Component
public class AuthServiceHystrix implements AuthServiceClient {
    @Override
    public JWT getToken(String authorization, String type, String username, String password) {
        System.out.println("获取TOKEN接口错误！");
        return null;
    }
}
