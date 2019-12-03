package com.egrand.wfw.ram.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "egd_sec_role")
public class Role {

    /**
     * 角色ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 角色名称
     */
    @Column(nullable = false)
    private String roleName;

    /**
     * 用户状态(0:启用;1:禁用;2:删除)
     */
    @Column
    private int status;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "egd_sec_role_privilege", joinColumns = @JoinColumn(name = "role_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "privilege_id", referencedColumnName = "id"))
    private Set<Privilege> privileges = new HashSet<>();

    /**
     * 创建时间
     */
    @Column
    private Date createDate;

    /**
     * 更新时间
     */
    @Column
    private Date updateDate;

    /**
     * 备注
     */
    @Column
    private String comments;

}
