package com.egrand.provider.ram.service;

import com.egrand.provider.ram.dao.UserDao;
import com.egrand.provider.ram.model.domain.User;
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

    public User findById(Long id){
        return this.userDao.findById(id);
    }

    public User save(User user){
        return this.userDao.save(user);
    }

    public void delete(Long id){
        this.userDao.delete(id);
    }
}
