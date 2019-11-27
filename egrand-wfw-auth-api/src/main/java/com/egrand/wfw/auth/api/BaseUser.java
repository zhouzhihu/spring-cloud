package com.egrand.wfw.auth.api;

import com.egrand.commons.lang.JsonUtils;
import java.io.Serializable;
import java.util.Map;

public class BaseUser<T>
        implements Serializable
{
    private String tgtId;
    private String stId;
    private String username;
    private String server;
    private String name;
    private Map map;
    private String id;
    private String accountName;
    private String password;
    private String phone;
    private Integer enabled;
    private Integer appType;
    private String clientId;
    private String clientSecret;
    private String grantTypes;
    private String webServerRedirectUri;
    private String resourceIds;
    private String taxCode;
    private String showName;
    private String identityId;
    private String taxReferred;
    private String accountCode;
    private String email;
    private String superTaxCode;
    private String scope;
    private String taxName;
    private String superTaxName;
    private String accountId;
    private String identityCode;
    private T data;

    public BaseUser() {}

    public static BaseUser buildClientDetails(String clientId, String clientSecret, String scope, String grantTypes, String webServerRedirectUri, String resourceIds) {
        BaseUser client = new BaseUser();
        client.setClientId(clientId);
        client.setClientSecret(clientSecret);
        client.setGrantTypes(grantTypes);
        client.setWebServerRedirectUri(webServerRedirectUri);
        client.setResourceIds(resourceIds);
        client.setScope(scope);
        return client;
    }

    public BaseUser(String id, String accountName, String password, String identityId) {
        this.id = id;
        this.accountName = accountName;
        this.password = password;
        this.identityId = identityId;
        this.enabled = Integer.valueOf(1);
    }

    public String getId() { return this.id; }

    public void setId(String id) { this.id = id; }

    public String getPassword() { return this.password; }

    public void setPassword(String password) { this.password = password; }

    public String getPhone() { return this.phone; }

    public void setPhone(String phone) { this.phone = phone; }

    public Integer getEnabled() { return this.enabled; }

    public void setEnabled(Integer enabled) { this.enabled = enabled; }

    public Integer getAppType() { return this.appType; }

    public void setAppType(Integer appType) { this.appType = appType; }

    public String getClientId() { return this.clientId; }

    public void setClientId(String clientId) { this.clientId = clientId; }

    public String getClientSecret() { return this.clientSecret; }

    public void setClientSecret(String clientSecret) { this.clientSecret = clientSecret; }

    public String getGrantTypes() { return this.grantTypes; }

    public void setGrantTypes(String grantTypes) { this.grantTypes = grantTypes; }

    public String getWebServerRedirectUri() { return this.webServerRedirectUri; }

    public void setWebServerRedirectUri(String webServerRedirectUri) { this.webServerRedirectUri = webServerRedirectUri; }

    public String getResourceIds() { return this.resourceIds; }

    public void setResourceIds(String resourceIds) { this.resourceIds = resourceIds; }

    public String getTaxCode() { return this.taxCode; }

    public void setTaxCode(String taxCode) { this.taxCode = taxCode; }

    public String getShowName() { return this.showName; }

    public void setShowName(String showName) { this.showName = showName; }

    public String getIdentityId() { return this.identityId; }

    public void setIdentityId(String identityId) { this.identityId = identityId; }

    public String getScope() { return this.scope; }

    public void setScope(String scope) { this.scope = scope; }

    public T getData() { return this.data; }

    public void setData(T data) { this.data = data; }

    public String getAccountName() { return this.accountName; }

    public void setAccountName(String accountName) { this.accountName = accountName; }

    public String getTaxReferred() { return this.taxReferred; }

    public void setTaxReferred(String taxReferred) { this.taxReferred = taxReferred; }

    public String getAccountCode() { return this.accountCode; }

    public void setAccountCode(String accountCode) { this.accountCode = accountCode; }

    public String getEmail() { return this.email; }

    public void setEmail(String email) { this.email = email; }

    public String getSuperTaxCode() { return this.superTaxCode; }

    public void setSuperTaxCode(String superTaxCode) { this.superTaxCode = superTaxCode; }

    public String getTaxName() { return this.taxName; }

    public void setTaxName(String taxName) { this.taxName = taxName; }

    public String getSuperTaxName() { return this.superTaxName; }

    public void setSuperTaxName(String superTaxName) { this.superTaxName = superTaxName; }

    public String getAccountId() { return this.accountId; }

    public void setAccountId(String accountId) { this.accountId = accountId; }

    public String getTgtId() { return this.tgtId; }

    public void setTgtId(String tgtId) { this.tgtId = tgtId; }

    public String getStId() { return this.stId; }

    public void setStId(String stId) { this.stId = stId; }

    public String getUsername() { return this.username; }

    public void setUsername(String username) { this.username = username; }

    public String getServer() { return this.server; }

    public void setServer(String server) { this.server = server; }

    public String getName() { return this.name; }

    public void setName(String name) { this.name = name; }

    public Map getMap() { return this.map; }

    public void setMap(Map map) { this.map = map; }

    public String getIdentityCode() { return this.identityCode; }

    public void setIdentityCode(String identityCode) { this.identityCode = identityCode; }

    public String toString() { return JsonUtils.obj2String(this); }
}