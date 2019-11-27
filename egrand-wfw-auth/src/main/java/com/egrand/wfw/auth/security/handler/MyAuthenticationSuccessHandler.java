package com.egrand.wfw.auth.security.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.egrand.wfw.auth.security.SpringUtils;
import com.egrand.wfw.auth.security.model.MyUserDetail;
import com.egrand.wfw.auth.security.service.DiyClientDetailsService;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.UnapprovedClientAuthenticationException;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

public class MyAuthenticationSuccessHandler
        extends SavedRequestAwareAuthenticationSuccessHandler {
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        DiyClientDetailsService clientDetailsService = (DiyClientDetailsService) SpringUtils.getBean("diyClientDetailsService");
        DefaultTokenServices authorizationServerTokenServices = new DefaultTokenServices();
        authorizationServerTokenServices.setTokenStore((TokenStore) new RedisTokenStore((RedisConnectionFactory) SpringUtils.getBean("redisConnectionFactory")));
        ObjectMapper objectMapper = new ObjectMapper();
        String type = request.getHeader("Accept");
        if (!type.contains("text/html")) {
            MyUserDetail userDetail = (MyUserDetail) authentication.getPrincipal();
            String clientId = userDetail.getUsername();
            String clientSecret = userDetail.getBaseUser().getPassword();
            ClientDetails clientDetails = clientDetailsService.loadClientByClientId(clientId);
            if (null == clientDetails)
                throw new UnapprovedClientAuthenticationException("clientId不存在" + clientId);
            if (!StringUtils.equals(clientDetails.getClientSecret(), clientSecret)) {
                throw new UnapprovedClientAuthenticationException("clientSecret不匹配" + clientId);
            }
            TokenRequest tokenRequest = new TokenRequest(MapUtils.EMPTY_MAP, clientId, clientDetails.getScope(), "custom");
            OAuth2Request oAuth2Request = tokenRequest.createOAuth2Request(clientDetails);
            OAuth2Authentication oAuth2Authentication = new OAuth2Authentication(oAuth2Request, authentication);
            OAuth2AccessToken token = authorizationServerTokenServices.createAccessToken(oAuth2Authentication);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(objectMapper.writeValueAsString(token));
        } else {
            super.onAuthenticationSuccess(request, response, authentication);
        }
    }
}