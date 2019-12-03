package com.egrand.wfw.ram.service;

import com.egrand.wfw.ram.dao.UserDao;
import com.egrand.wfw.ram.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户
     */
    public User findByUsername(String username){
        return this.userDao.findByUsername(username);
    }
}
