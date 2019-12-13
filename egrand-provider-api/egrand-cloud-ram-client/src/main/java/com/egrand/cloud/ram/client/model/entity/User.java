package com.egrand.cloud.ram.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.egrand.core.mybatis.base.entity.AbstractEntity;
import java.util.Date;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * 
 *
 * @author ZZH
 * @date 2019-12-12
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@TableName("egd_org_user")
@ApiModel(value="User对象", description="")
public class User extends AbstractEntity {

    private static final long serialVersionUID=1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    private String comments;

    private Date createDate;

    private String displayName;

    private String email;

    private String isTmpUser;

    private Date lastLoginDate;

    private String mobilePhone;

    private String password;

    private Integer status;

    private String type;

    private Date updateDate;

    private String username;

    private Date validityEndDate;

    private Date validityStartDate;


}
