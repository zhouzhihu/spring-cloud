package com.egrand.wfw.oauth.user.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法级别的保护
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

//    @Autowired
//    private TokenStore tokenStore;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        //配置哪些请求需要验证
        http.authorizeRequests()
                .antMatchers("/user/findByUsername/**").permitAll()
                .antMatchers("/user/login/**").permitAll()
                .antMatchers("/user/getAccessToken/**").permitAll()
                .antMatchers("/user/loginByFeignClient/**").permitAll()
                .antMatchers("/role/**").permitAll()
                .anyRequest().authenticated();
    }

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenStore(tokenStore);
//    }

}
