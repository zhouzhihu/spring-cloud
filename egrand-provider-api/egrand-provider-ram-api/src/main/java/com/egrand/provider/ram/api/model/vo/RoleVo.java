package com.egrand.provider.ram.api.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-05-10
 * Time: 21:03
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleVo implements Serializable {
    private static final long serialVersionUID = 2179037393108205286L;
    private Integer id;

    private String roleName;

    private String roleCode;
}
