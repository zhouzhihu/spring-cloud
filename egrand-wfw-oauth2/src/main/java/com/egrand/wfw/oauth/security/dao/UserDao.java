package com.egrand.wfw.oauth.security.dao;

import com.egrand.wfw.oauth.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
