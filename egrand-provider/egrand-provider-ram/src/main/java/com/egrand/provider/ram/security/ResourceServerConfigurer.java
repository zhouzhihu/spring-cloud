package com.egrand.provider.ram.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;

@Configuration
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)//开启方法级别的保护
public class ResourceServerConfigurer extends ResourceServerConfigurerAdapter {

//    @Autowired
//    private TokenStore tokenStore;

    @Value("${myoauth.white-list:}")
    String[] whiteList;

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(HttpMethod.DELETE, uris())).permitAll();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(HttpMethod.PUT, uris())).permitAll();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(HttpMethod.GET, uris())).permitAll();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(HttpMethod.POST, uris())).permitAll();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, uris())).permitAll();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().anyRequest()).authenticated();
    }

    private String[] uris() {
        String[] uris = {"/swagger-resources/**", "/swagger-ui.html", "/v2/**", "/webjars/**", "/resources/**", "/swagger-ui.html", "/swagger-resources/**", "/v2/api-docs", "/open/**","/user/query/**", "/role/query/**"};
        if (this.whiteList.length > 0) {
            String[] re = new String[uris.length + this.whiteList.length];
            System.arraycopy(uris, 0, re, 0, uris.length);
            for (int i = 0; i < this.whiteList.length; i++) {
                re[uris.length + i] = this.whiteList[i];
            }
            return re;
        }
        return uris;
    }

//    @Override
//    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
//        resources.tokenStore(tokenStore);
//    }

}
