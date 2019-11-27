package com.egrand.wfw.oauth.security.base;

public class BaseApiRole
{
    private String uri;
    private String method;
    private String roleCode;

    public BaseApiRole(String uri, String method, String roleCode) {
        this.uri = uri;
        this.method = method;
        this.roleCode = roleCode;
    }

    public BaseApiRole() {}

    public String getUri() { return this.uri; }

    public void setUri(String uri) { this.uri = uri; }

    public String getMethod() { return this.method; }

    public void setMethod(String method) { this.method = method; }

    public String getRoleCode() { return this.roleCode; }

    public void setRoleCode(String roleCode) { this.roleCode = roleCode; }
}
