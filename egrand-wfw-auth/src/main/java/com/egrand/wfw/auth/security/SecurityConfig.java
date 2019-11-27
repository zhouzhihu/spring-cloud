package com.egrand.wfw.auth.security;

import com.egrand.wfw.auth.security.RoleBasedVoter;
import com.egrand.wfw.auth.security.config.jinsan.JinsanAuthenticationSecurityConfig;
import com.egrand.wfw.auth.security.config.token.TokenAuthenticationSecurityConfig;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.UnanimousBased;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.ForwardAuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig
        extends WebSecurityConfigurerAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private TokenAuthenticationSecurityConfig tokenAuthenticationSecurityConfig;

    @Autowired
    private JinsanAuthenticationSecurityConfig jinsanAuthenticationSecurityConfig;

    @Value("${spring.sercurity.white-url:/oauth/**,/authentication/**,/oauthLogin,/static/**,/login/**}")
    private String[] whiteUri;

    @Autowired
    private RoleBasedVoter roleBasedVoter;

    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        Set<String> set = new HashSet<>(Arrays.asList(this.whiteUri));
        set.add("/oauth/**");
        set.add("/authentication/**");
        String[] uri = set.toArray(new String[0]);
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(HttpMethod.GET, uri)).permitAll();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(HttpMethod.POST, uri)).permitAll();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().antMatchers(HttpMethod.OPTIONS, new String[]{"/oauth/**", "/authentication/**"})).permitAll();
        ((ExpressionUrlAuthorizationConfigurer.AuthorizedUrl) http.authorizeRequests().anyRequest()).authenticated();
        ((FormLoginConfigurer) ((FormLoginConfigurer) http.formLogin()
                .loginPage("/authentication/require")
                .successHandler((AuthenticationSuccessHandler) new ForwardAuthenticationSuccessHandler("/users/current")))
                .failureUrl("/oauth/approvale/error"))
                .loginProcessingUrl("/authentication/form");
        http.apply((SecurityConfigurerAdapter) this.tokenAuthenticationSecurityConfig);
        http.apply((SecurityConfigurerAdapter) this.jinsanAuthenticationSecurityConfig);
        http.authorizeRequests().accessDecisionManager(accessDecisionManager());
    }


    @Bean
    public AccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = Arrays.asList((AccessDecisionVoter<? extends Object>[]) new AccessDecisionVoter[]{(AccessDecisionVoter) new WebExpressionVoter(), (AccessDecisionVoter) this.roleBasedVoter, (AccessDecisionVoter) new AuthenticatedVoter()});
        return (AccessDecisionManager) new UnanimousBased(decisionVoters);
    }

    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
