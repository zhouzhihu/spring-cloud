package com.egrand.wfw.oauth.security.service;

import com.egrand.wfw.oauth.security.base.BaseApiRole;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BaseRoleService
{
    public String getRoles(String uri, String method) {
        List<BaseApiRole> list = new ArrayList<>();
        list.add(new BaseApiRole("/hello", "GET", "ROLE_USER"));
        list.add(new BaseApiRole("/hello", "POST", "ROLE_USER"));
        for (int i = 0; i < list.size(); i++) {
            BaseApiRole apiRole = list.get(i);
            if (apiRole.getUri().equals(uri) && apiRole.getMethod().equals(method)) {
                return apiRole.getRoleCode();
            }
        }
        return null;
    }
}

