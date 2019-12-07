package com.egrand.provider.ram.dao;

import com.egrand.provider.ram.model.domain.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RoleDao extends JpaRepository<Role, Long> {
    @Query(value="select r.id, r.role_name, r.role_code from egd_sec_role r, egd_org_group_roles gr where r.id = gr.role_id and gr.group_id in (select g.id from egd_org_group g, egd_org_group_user gu where g.id = gu.group_id and gu.user_id =:userId)", nativeQuery = true)
    List<Object[]> getRoleByUserId(@Param("userId") Long userId);
}
