package com.egrand.cloud.ram.client.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.egrand.core.mybatis.base.entity.AbstractEntity;
import com.baomidou.mybatisplus.annotation.IdType;
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
@TableName("egd_org_group_user")
@ApiModel(value="GroupUser对象", description="")
public class GroupUser extends AbstractEntity {

    private static final long serialVersionUID=1L;

    @TableId(value = "user_id", type = IdType.ID_WORKER)
    private Long userId;

    private Long groupId;


}
