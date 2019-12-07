package com.egrand.provider.ram.model.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "egd_sec_model")
public class Model {
    /**
     * 权限ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 权限名称
     */
    @Column(nullable = false)
    private String modelName;

    /**
     * 权限编码
     */
    @Column(nullable = false)
    private String modelCode;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Model parent;

    //子公司
    @OneToMany(mappedBy = "parent")
    private Set<Model> children = new HashSet<>();

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
     * 图标
     */
    @Column
    private String icon;

    /**
     * 是否内置权限(Y/N)
     */
    @Column
    private String innerFlag;

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
