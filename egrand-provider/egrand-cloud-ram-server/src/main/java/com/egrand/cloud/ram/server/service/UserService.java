package com.egrand.cloud.ram.server.service;

import com.egrand.cloud.ram.client.model.UserAccount;
import com.egrand.cloud.ram.client.model.entity.User;
import com.egrand.core.mybatis.base.service.IBaseService;

/**
 *  服务类
 *
 * @author ZZH
 * @date 2019-12-12
 */
public interface UserService extends IBaseService<User> {
    /**
     * 登录
     *
     * @param username 登陆账号
     * @return
     */
    UserAccount login(String username);
}
