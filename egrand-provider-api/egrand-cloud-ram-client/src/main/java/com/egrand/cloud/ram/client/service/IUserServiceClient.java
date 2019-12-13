package com.egrand.cloud.ram.client.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.egrand.cloud.ram.client.model.UserAccount;
import com.egrand.cloud.ram.client.model.entity.User;
import com.egrand.core.model.ResultBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

public interface IUserServiceClient {

    /**
     * 系统用户登录
     *
     * @param username
     * @return
     */
    @PostMapping("/user/login")
    ResultBody<UserAccount> userLogin(@RequestParam(value = "username") String username);

    @GetMapping(value = "/user/feign")
    ResultBody<String> feign(@RequestParam(value = "username") String username);
}
