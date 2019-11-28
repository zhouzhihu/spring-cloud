package com.egrand.wfw.oauth.user.security.dao;

import com.egrand.wfw.oauth.user.security.model.Role;
import com.egrand.wfw.oauth.user.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleDao extends JpaRepository<User, Long> {
    @Query(value="select r.* from role r, user_role ur where r.id=ur.role_id and ur.user_id=:userId", nativeQuery = true)
    List<Object[]> getRoleByUserId(@Param("userId") Integer userId);
}
