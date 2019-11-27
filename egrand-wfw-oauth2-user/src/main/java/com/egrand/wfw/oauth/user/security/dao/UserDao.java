package com.egrand.wfw.oauth.user.security.dao;

import com.egrand.wfw.oauth.user.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
