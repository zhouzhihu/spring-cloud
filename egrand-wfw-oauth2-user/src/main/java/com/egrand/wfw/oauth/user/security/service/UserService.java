package com.egrand.wfw.oauth.user.security.service;

import com.egrand.wfw.oauth.user.security.dao.UserDao;
import com.egrand.wfw.oauth.user.security.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService   {
    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Autowired
    private UserDao userDao;

    public User create(String username, String password) {
        User user=new User();
        user.setUsername(username);
        String hash = encoder.encode(password);
        user.setPassword(hash);
        User u=userDao.save(user);
        return u;
    }

    public User findByUsername(String username){
        return this.userDao.findByUsername(username);
    }
}
