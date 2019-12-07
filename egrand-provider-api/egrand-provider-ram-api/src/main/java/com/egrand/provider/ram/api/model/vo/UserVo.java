package com.egrand.provider.ram.api.model.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: Mr.Yangxiufeng
 * Date: 2018-05-10
 * Time: 21:00
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserVo implements Serializable {
    private static final long serialVersionUID = 3881610071550902762L;

    private Integer id;

    private String username;

    private String password;
}
