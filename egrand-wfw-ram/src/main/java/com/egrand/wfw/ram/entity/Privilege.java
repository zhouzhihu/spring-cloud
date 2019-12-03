package com.egrand.wfw.ram.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "egd_sec_privilege")
public class Privilege {

    /**
     * 权限ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 所属模块
     */
    @ManyToOne
    @JoinColumn(name = "model_id")
    private Model model;

    /**
     * 权限名称
     */
    @Column(nullable = false)
    private String privilegeName;

    /**
     * 权限编码
     */
    @Column(nullable = false)
    private String privilegeCode;

    /**
     * 用户状态(0:启用;1:禁用;2:删除)
     */
    @Column
    private int status;

    /**
     * 排序号
     */
    @Column
    private String orderNo;

    /**
     * 是否内置权限(Y/N)
     */
    @Column
    private String innerFlag;

    /**
     * 图标
     */
    @Column
    private String icon;

    /**
     * 权限类型(btn/url)
     */
    @Column
    private String type;

    /**
     * 权限路径
     */
    @Column
    private String urlPath;

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
