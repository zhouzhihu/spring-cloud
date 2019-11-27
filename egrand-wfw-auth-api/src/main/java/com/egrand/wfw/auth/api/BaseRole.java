package com.egrand.wfw.auth.api;

import com.egrand.commons.lang.JsonUtils;
import java.io.Serializable;
import org.springframework.security.core.GrantedAuthority;

public class BaseRole
        implements GrantedAuthority, Serializable
{
    private String id;
    private String pRoleCode;
    private String roleCode;
    private String roleName;
    private String remark;
    private String rolePath;
    private String roleIcon;
    private String httpMethod;
    private Integer operationType;
    private Integer sort;
    private Integer enabled;

    public BaseRole() {}

    public BaseRole(String id, String pRoleCode, String roleCode, String roleName, String remark, String rolePath, String roleIcon, String httpMethod, Integer operationType, Integer sort, Integer enabled) {
        this.id = id;
        this.pRoleCode = pRoleCode;
        this.roleCode = roleCode;
        this.roleName = roleName;
        this.remark = remark;
        this.rolePath = rolePath;
        this.roleIcon = roleIcon;
        this.httpMethod = httpMethod;
        this.operationType = operationType;
        this.sort = sort;
        this.enabled = enabled;
    }


    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getpRoleCode() { return this.pRoleCode; }

    public void setpRoleCode(String pRoleCode) { this.pRoleCode = pRoleCode; }

    public String getRoleCode() { return this.roleCode; }

    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }

    public String getRoleName() { return this.roleName; }

    public void setRoleName(String roleName) { this.roleName = roleName; }

    public String getRemark() { return this.remark; }

    public void setRemark(String remark) { this.remark = remark; }

    public String getRolePath() { return this.rolePath; }

    public void setRolePath(String rolePath) { this.rolePath = rolePath; }

    public String getRoleIcon() { return this.roleIcon; }

    public void setRoleIcon(String roleIcon) { this.roleIcon = roleIcon; }

    public String getHttpMethod() { return this.httpMethod; }

    public void setHttpMethod(String httpMethod) { this.httpMethod = httpMethod; }

    public Integer getOperationType() { return this.operationType; }

    public void setOperationType(Integer operationType) { this.operationType = operationType; }

    public Integer getSort() { return this.sort; }

    public void setSort(Integer sort) { this.sort = sort; }

    public Integer getEnabled() { return this.enabled; }

    public void setEnabled(Integer enabled) { this.enabled = enabled; }

    public String getAuthority() { return this.roleCode; }

    public String toString() { return JsonUtils.obj2String(this); }
}
