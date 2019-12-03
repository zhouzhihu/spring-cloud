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
@Table(name = "egd_org_user")
public class User {
    /**
     * 用户ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 显示名称
     */
    @Column(nullable = false)
    private String displayName;

    /**
     * 用户名
     */
    @Column(nullable = false,  unique = true)
    private String username;

    /**
     * 用户密码
     */
    @Column(nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(name = "egd_org_group_user", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "group_id", referencedColumnName = "id"))
    private Set<Group> groups = new HashSet<>();

    /**
     * 用户状态(0:启用;1:禁用;2:删除)
     */
    @Column
    private int status;

    /**
     * 用户类型
     */
    @Column
    private String type;

    /**
     * 临时用户（Y/N）
     */
    @Column
    private String isTmpUser;

    /**
     * 邮件
     */
    @Column
    private String email;

    /**
     * 用户手机号码
     */
    @Column
    private String mobilePhone;

    /**
     * 有效开始时间
     */
    @Column
    private Date validityStartDate;

    /**
     * 有效结束时间
     */
    @Column
    private Date validityEndDate;

    /**
     * 创建时间
     */
    @Column
    private Date createDate;

    /**
     * 上次登录时间
     */
    @Column
    private Date lastLoginDate;

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
