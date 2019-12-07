package com.egrand.provider.ram.dao;

import com.egrand.provider.ram.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserDao extends JpaRepository<User, Long> {
    /**
     * 根据用户名查找用户
     * @param username 用户名
     * @return 用户
     */
    User findByUsername(String username);

    /**
     * 根据ID查找用户
     * @param id
     * @return
     */
    User findById(Long id);
}