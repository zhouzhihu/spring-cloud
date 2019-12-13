package com.egrand.wfw.oauth.security.service.feign;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.egrand.cloud.ram.client.model.UserAccount;
import com.egrand.cloud.ram.client.model.entity.User;
import com.egrand.core.model.ResultBody;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class UserServiceClientImpl implements UserServiceClient {

    @Override
    public ResultBody<UserAccount> userLogin(String username) {
        System.out.println("调用失败！");
        return ResultBody.failed();
    }

    @Override
    public ResultBody<String> feign(String username) {
        System.out.println("调用失败！");
        return ResultBody.failed();
    }
}
