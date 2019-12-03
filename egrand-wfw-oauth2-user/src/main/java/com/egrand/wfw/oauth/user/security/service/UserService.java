package com.egrand.wfw.oauth.user.security.service;

import com.egrand.wfw.oauth.user.security.dao.UserDao;
import com.egrand.wfw.oauth.user.security.exception.UserLoginException;
import com.egrand.wfw.oauth.user.security.model.JWT;
import com.egrand.wfw.oauth.user.security.model.User;
import com.egrand.wfw.oauth.user.security.model.UserLoginDto;
import com.egrand.wfw.oauth.user.security.utils.BPwdEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class UserService   {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AuthServiceClient client;

    public User create(String username, String password) {
        User user=new User();
        user.setUsername(username);
        String hash = BPwdEncoderUtil.BCryptPassword(password);
        user.setPassword(hash);
        User u=userDao.save(user);
        return u;
    }

    public User findByUsername(String username){
        return this.userDao.findByUsername(username);
    }

    public User findByUserId(Long userId){
        return this.userDao.findById(userId);
    }

    public UserLoginDto login(String username, String password){
        User user=this.userDao.findByUsername(username);
        if (null == user) {
            throw new UserLoginException("error username");
        }
        if(!BPwdEncoderUtil.matches(password,user.getPassword())){
            throw new UserLoginException("error password");
        }
        String client_secret = "service-hi:123456";
        client_secret = "Basic "+ Base64.getEncoder().encodeToString(client_secret.getBytes());
        System.out.println("===================Get Token=================");
        System.out.println("username = " + username);
        System.out.println("password = " + password);
        System.out.println("client_secret = " + client_secret);
        System.out.println("===================Get Token=================");
        // 获取token
        JWT jwt = client.getToken(client_secret,"password",username,password);
        // 获得用户菜单
        if(jwt==null){
            throw new UserLoginException("error internal");
        }
        UserLoginDto userLoginDTO=new UserLoginDto();
        userLoginDTO.setJwt(jwt);
        userLoginDTO.setUser(user);
        return userLoginDTO;

    }
}
