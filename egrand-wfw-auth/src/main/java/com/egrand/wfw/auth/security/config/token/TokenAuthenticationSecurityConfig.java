package com.egrand.wfw.auth.security.config.token;

import com.egrand.wfw.auth.security.base.BaseAuthenticationSecurityConfig;
import com.egrand.wfw.auth.security.service.DiyTokenUserDetailServer;
import javax.servlet.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.AuthenticationUserDetailsService;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;
import org.springframework.stereotype.Component;

@Component
public class TokenAuthenticationSecurityConfig extends BaseAuthenticationSecurityConfig {
    @Autowired
    private DiyTokenUserDetailServer tokenUserDetailServer;
    @Autowired
    private AuthenticationManager authenticationManager;

    private AuthenticationProvider preAuthenticationProvider() {
        PreAuthenticatedAuthenticationProvider provider = new PreAuthenticatedAuthenticationProvider();
        provider.setPreAuthenticatedUserDetailsService((AuthenticationUserDetailsService) this.tokenUserDetailServer);
        return (AuthenticationProvider) provider;
    }

    public TokenPreAuthenticationFilter headerAuthenticationFilter() throws Exception {
        return new TokenPreAuthenticationFilter(this.authenticationManager);
    }

    public void configure(HttpSecurity http) throws Exception {
        http.authenticationProvider(preAuthenticationProvider())
                .addFilter((Filter) headerAuthenticationFilter());
    }
}
