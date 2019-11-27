package com.egrand.wfw.auth.security.service;

import com.egrand.wfw.auth.api.BaseUser;
import com.egrand.wfw.auth.security.model.UserDetailFactory;
import com.egrand.wfw.auth.service.AppAuthService;
import com.egrand.wfw.component.cache.redis.RedisCacheService;
import com.egrand.commons.lang.JsonUtils;
import com.egrand.commons.lang.model.RestResponse;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.stereotype.Service;

@Service("diyClientDetailsService")
@Configuration
public class DiyClientDetailsService
        implements ClientDetailsService
{
    private Logger logger = LoggerFactory.getLogger(DiyClientDetailsService.class);

    @Autowired
    private AppAuthService appAuthService;
    @Autowired
    private RedisCacheService cacheManager;



    public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
        BaseUser user = null;
        RestResponse<Map> response = this.appAuthService.yyglGetQueryByAP(clientId);
        if (response == null) {
            throw new RuntimeException("获取应用信息失败");
        }
        user = BaseUser.buildClientDetails((String)((Map)response
                .getData()).get("account"), (String)((Map)response
                .getData()).get("pwd"), "server", "client_credentials,refresh_token,password,authorization_code", "http://baidu.com", "");
        String accountName = (String)((Map)response.getData()).get("oauthAccountName");
        String password = (String)((Map)response.getData()).get("oauthPassword");
        user.setAccountName(accountName);
        user.setPassword(password);
        this.cacheManager.set("oauth_info:" + clientId, JsonUtils.obj2String(user), 1800L);
        if (user == null) {
            throw new RuntimeException("客户端鉴权信息错误");
        }
        return (ClientDetails)UserDetailFactory.createClientDetails(user);
    }
}
