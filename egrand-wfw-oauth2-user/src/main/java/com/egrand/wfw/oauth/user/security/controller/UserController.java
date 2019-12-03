package com.egrand.wfw.oauth.user.security.controller;

import com.egrand.commons.lang.model.ApiResponse;
import com.egrand.wfw.oauth.user.security.model.User;
import com.egrand.wfw.oauth.user.security.model.UserLoginDto;
import com.egrand.wfw.oauth.user.security.model.UserLoginParamDto;
import com.egrand.wfw.oauth.user.security.service.UserService;
import com.egrand.wfw.oauth.user.security.utils.BPwdEncoderUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.OAuth2ClientProperties;
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

    @RequestMapping(value = "/registry",method = RequestMethod.POST)
    public User createUser(@RequestParam("username") String username
            , @RequestParam("password") String password) {
        return userService.create(username,password);
    }

    @GetMapping("findByUsername/{username}")
    public ApiResponse findByUsername(@PathVariable("username") String username){
        User user = userService.findByUsername(username);
        if (user == null){
            return ApiResponse.failed("100","用户不存在");
        }
        return ApiResponse.success(user);
    }

    @GetMapping("findByUserId/{userId}")
    public ApiResponse findById(@PathVariable("userId") Long userId){
        User user =userService.findByUserId(userId);
        if (user == null){
            return ApiResponse.failed("100","用户不存在");
        }
        return ApiResponse.success(user);
    }

    @PostMapping("/loginByFeignClient")
    public UserLoginDto login(@RequestParam("username") String username , @RequestParam("password") String password){
        //参数判断，省略
        return this.userService.login(username,password);
    }

    @RequestMapping("/login")
    public ResponseEntity<OAuth2AccessToken> login(@Valid UserLoginParamDto loginDto, BindingResult bindingResult) throws Exception {
        if (bindingResult.hasErrors())
            throw new Exception("登录信息错误，请确认后再试");
        User user = userService.findByUsername(loginDto.getUsername());
        if (null == user)
            throw new Exception("用户为空，出错了");
        if (!BPwdEncoderUtil.matches(loginDto.getPassword(), user.getPassword().replace("{bcrypt}","")))
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

    @RequestMapping(value = "/getAccessToken", method = RequestMethod.POST)
    public OAuth2AccessToken getAccessToken() throws Exception {
        String tokenUri = "http://192.168.109.1:21031/oauth/token";

        ResourceOwnerPasswordResourceDetails resourceDetails = new ResourceOwnerPasswordResourceDetails();

        resourceDetails.setAccessTokenUri(tokenUri);
        resourceDetails.setClientId("service-hi");
        resourceDetails.setClientSecret("123456");
        resourceDetails.setUsername("web_study");
        resourceDetails.setPassword("wiki2012");
        resourceDetails.setGrantType("password");
        resourceDetails.setScope(Arrays.asList("server"));
        resourceDetails.setAuthenticationScheme(AuthenticationScheme.form);

        DefaultOAuth2ClientContext clientContext = new DefaultOAuth2ClientContext();

        OAuth2RestTemplate oauth2RestTemplate = new OAuth2RestTemplate(resourceDetails, clientContext);

        OAuth2AccessToken token = oauth2RestTemplate.getAccessToken();
        return token;
    }
}