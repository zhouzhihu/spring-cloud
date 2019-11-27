package com.egrand.wfw.auth.security.config.jinsan;

import com.egrand.wfw.auth.security.config.jinsan.JinsanAuthenticationProvider;
import com.egrand.wfw.auth.security.config.jinsan.JinsanLoginAuthenticationFilter;
import com.egrand.wfw.auth.security.handler.MyAuthenticationSuccessHandler;
import com.egrand.wfw.auth.security.service.DiyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.SecurityBuilder;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;
import javax.servlet.Filter;

@Component
public class JinsanAuthenticationSecurityConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    public void configure(HttpSecurity http) throws Exception {
        JinsanLoginAuthenticationFilter authenticationFilter = new JinsanLoginAuthenticationFilter();
        authenticationFilter.setAuthenticationManager((AuthenticationManager)http.getSharedObject(AuthenticationManager.class));
        authenticationFilter.setAuthenticationSuccessHandler((AuthenticationSuccessHandler)new MyAuthenticationSuccessHandler());

        JinsanAuthenticationProvider provider = new JinsanAuthenticationProvider();
        provider.setUserDetailsService((UserDetailsService)this.diyUserDetailService);

        http.authenticationProvider((AuthenticationProvider)provider)
                .addFilterAfter((Filter)authenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    private DiyUserDetailService diyUserDetailService;
}
