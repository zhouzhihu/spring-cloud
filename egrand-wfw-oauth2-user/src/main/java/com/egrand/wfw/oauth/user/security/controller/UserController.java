package com.egrand.wfw.oauth.user.security.controller;

import com.egrand.commons.base.model.RestResponse;
import com.egrand.provider.ram.api.model.vo.UserVo;
import com.egrand.provider.ram.api.service.UserService;
import com.egrand.wfw.oauth.user.security.model.UserLoginParamDto;
import com.egrand.wfw.oauth.user.security.utils.BPwdEncoderUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.grant.password.ResourceOwnerPasswordResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.validation.Valid;
import java.util.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OAuth2ClientProperties oAuth2ClientProperties;

    @Autowired
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

    @Autowired
    private RestTemplate restTemplate;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

//    @Autowired
//    private ResourceOwnerPasswordResourceDetails resourceOwnerPasswordResourceDetails;

    @RequestMapping("/login")
    public ResponseEntity<OAuth2AccessToken> login(@Valid UserLoginParamDto loginDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors())
            throw new Exception("登录信息错误，请确认后再试");
        RestResponse userResult = this.userService.findByUsername(loginDto.getUsername());
        if(!userResult.isSuccess())
            throw new Exception(userResult.getErrorMsg());
        if (null == userResult.getData())
            throw new Exception("用户为空，出错了");
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(userResult.getData(),userVo);
        if (!BPwdEncoderUtil.matches(loginDto.getPassword(), userVo.getPassword().replace("{bcrypt}","")))
            throw new Exception("密码不正确");
        String client_secret = oAuth2ClientProperties.getClientId()+":"+oAuth2ClientProperties.getClientSecret();
        client_secret = "Basic "+Base64.getEncoder().encodeToString(client_secret.getBytes());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.set("Authorization",client_secret);
        //授权请求信息
        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();
        map.put("username", Collections.singletonList(loginDto.getUsername()));
        map.put("password", Collections.singletonList(loginDto.getPassword()));
        map.put("grant_type", Collections.singletonList(oAuth2ProtectedResourceDetails.getGrantType()));
        map.put("scope", oAuth2ProtectedResourceDetails.getScope());
        System.out.println("===================Get Token=================");
        System.out.println("clientId = " + oAuth2ClientProperties.getClientId());
        System.out.println("clientSecret = " + oAuth2ClientProperties.getClientSecret());
        System.out.println("username = " + loginDto.getUsername());
        System.out.println("password = " + loginDto.getPassword());
        System.out.println("grant_type = " + oAuth2ProtectedResourceDetails.getGrantType());
        System.out.println("scope = " + oAuth2ProtectedResourceDetails.getScope());
        System.out.println("accessTokenUri = " + oAuth2ProtectedResourceDetails.getAccessTokenUri());
        System.out.println("client_secret = " + client_secret);
        System.out.println("===================Get Token=================");
        //HttpEntity
        HttpEntity httpEntity = new HttpEntity(map,httpHeaders);
        System.out.println("httpEntity = " + httpEntity.toString());
        //获取 Token
        return restTemplate.exchange(oAuth2ProtectedResourceDetails.getAccessTokenUri(), HttpMethod.POST,httpEntity,OAuth2AccessToken.class);
    }

//    @RequestMapping(value = "/getAccessToken")
//    public OAuth2AccessToken getAccessToken(@Valid UserLoginParamDto loginDto, BindingResult bindingResult) throws Exception {
//        if (bindingResult.hasErrors())
//            throw new Exception("登录信息错误，请确认后再试");
//        RestResponse userResult = this.userService.findByUsername(loginDto.getUsername());
//        if(!userResult.isSuccess())
//            throw new Exception(userResult.getErrorMsg());
//        if (null == userResult.getData())
//            throw new Exception("用户为空，出错了");
//        UserVo userVo = new UserVo();
//        BeanUtils.copyProperties(userResult.getData(),userVo);
//        if (!BPwdEncoderUtil.matches(loginDto.getPassword(), userVo.getPassword().replace("{bcrypt}","")))
//            throw new Exception("密码不正确");
//        resourceOwnerPasswordResourceDetails.setUsername(userVo.getUsername());
//        resourceOwnerPasswordResourceDetails.setPassword(userVo.getPassword());
//        OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(resourceOwnerPasswordResourceDetails);
//        OAuth2AccessToken token = oAuth2RestTemplate.getAccessToken();
//        return token;
//    }
}