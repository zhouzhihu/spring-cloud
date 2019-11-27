package com.egrand.wfw.auth.security.service;

import com.egrand.wfw.auth.api.BaseUser;
import com.egrand.wfw.auth.security.model.UserDetailFactory;
import com.egrand.wfw.auth.service.UserAuthService;
import com.egrand.wfw.component.cache.redis.RedisCacheService;
import com.egrand.commons.lang.BeanUtils;
import com.egrand.commons.lang.JsonUtils;
import com.egrand.commons.lang.model.RestResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Service;

@Service("diyTokenUserDetailServer")
public class DiyTokenUserDetailServer
        implements AuthenticationUserDetailsService<PreAuthenticatedAuthenticationToken> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${hyht.security.oauth2.defult.username:}")
    private String defultUserName;
    @Value("${hyht.security.oauth2.defult.password:}")
    private String defultPassword;
    @Autowired
    private UserAuthService appAuthService;

    @Autowired
    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private RedisCacheService cacheManager;


    @Bean
    private RedisTokenStore redisTokenStore() {
        RedisTokenStore redisTokenStore = new RedisTokenStore(this.redisConnectionFactory);
        return redisTokenStore;
    }

    public UserDetails loadUserDetails(PreAuthenticatedAuthenticationToken authToken) throws UsernameNotFoundException {
        String token = (String) authToken.getPrincipal();
        if (token != null) {
            if (token.indexOf("Bearer ") == 0) {
                String check = token.replace("Bearer ", "");
                if (check.indexOf("token_api_") == 0) {
                    String jsonObj = this.cacheManager.get(check);
                    BaseUser baseUser = (BaseUser) JsonUtils.string2Obj(jsonObj, BaseUser.class);
                    if (baseUser != null && StringUtils.isNotBlank(baseUser.getUsername())) {
                        this.logger.info("请求用户：{}", baseUser.getUsername());
                        if (baseUser.getStId() != null) {
                            this.cacheManager.expire(baseUser.getStId(), 2100L);
                        }
                        this.cacheManager.expire(baseUser.getTgtId(), 2100L);
                        this.cacheManager.expire(token, 2100L);
                    }
                    return (UserDetails) UserDetailFactory.createUserDetail(baseUser);
                }
                OAuth2AccessToken accessToken = redisTokenStore().readAccessToken(check);
                OAuth2Authentication oAuth2Authentication1 = redisTokenStore().readAuthentication(check);
                this.logger.info("当前验证的token是：{}", check);
                this.logger.info("当前验证的token结果是：{}", oAuth2Authentication1);
                OAuth2Authentication oAuth2Authentication = oAuth2Authentication1;
                OAuth2Request oAuth2Request = oAuth2Authentication.getOAuth2Request();
                Map<String, String> parameters = oAuth2Request.getRequestParameters();
                String clientId = parameters.get("client_id");
                Object userDetail = oAuth2Authentication1.getPrincipal();
                BaseUser user1 = null;
                if (userDetail instanceof String) {
                    String key = check.replace("token_api_", "");
                    String userJson = this.cacheManager.get("oauth_info:" + clientId);
                    BaseUser oauthUser = (BaseUser) JsonUtils.string2Obj(userJson, BaseUser.class);
                    String accountName = StringUtils.isNotEmpty(oauthUser.getAccountName()) ? oauthUser.getAccountName() : this.defultUserName;
                    String password = StringUtils.isNotEmpty(oauthUser.getPassword()) ? oauthUser.getPassword() : this.defultPassword;
                    if (StringUtils.isNotBlank(accountName) && StringUtils.isNotBlank(password)) {
                        RestResponse<ArrayList> response = this.appAuthService.getIdentity(accountName, password);
                        if (((ArrayList) response.getData()).size() > 0) {
                            Map<String, String> map = ((ArrayList<Map<String, String>>) response.getData()).get(0);
                            String id = map.get("id");
                            if (StringUtils.isNotBlank(id)) {
                                RestResponse response1 = this.appAuthService.getInfo(id);
                                Object obj = response1.getData();
                                try {
                                    user1 = new BaseUser();
                                    BeanUtils.copyProperties(user1, obj);
                                    this.cacheManager.set(key, JsonUtils.obj2String(user1), 1800L);
                                } catch (IllegalAccessException e) {
                                    e.printStackTrace();
                                } catch (InvocationTargetException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    } else {
                        user1 = new BaseUser();
                    }
                    if (oAuth2Authentication1 instanceof OAuth2Authentication) {
                        if (user1 == null) user1 = new BaseUser();
                        user1.setClientId(parameters.get("client_id"));
                        user1.setScope(parameters.get("scope"));
                        user1.setGrantTypes(parameters.get("grant_type"));
                    }
                } else {
                    Map<String, Object> map = BeanUtils.objectToMap(userDetail);
                    Map<String, String> user = (Map<String, String>) map.get("baseUser");
                    user1 = (BaseUser) JsonUtils.string2Obj(JsonUtils.obj2String(user), BaseUser.class);
                }
                return (UserDetails) UserDetailFactory.createUserDetail(user1);
            }
            if (token.indexOf("token_api_") == 0) {

                String jsonObj = this.cacheManager.get(token);
                BaseUser baseUser = (BaseUser) JsonUtils.string2Obj(jsonObj, BaseUser.class);
                if (baseUser != null && StringUtils.isNotBlank(baseUser.getUsername())) {
                    this.logger.info("请求用户：{}", baseUser.getUsername());

                    if (baseUser.getStId() != null) {
                        this.cacheManager.expire(baseUser.getStId(), 2100L);
                    }
                    this.cacheManager.expire(baseUser.getTgtId(), 2100L);
                    this.cacheManager.expire(token, 2100L);
                }
                return (UserDetails) UserDetailFactory.createUserDetail(baseUser);
            }
        }
        throw new BadCredentialsException("无效凭证");
    }
}
