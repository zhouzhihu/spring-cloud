package com.egrand.wfw.auth.security.config.jinsan;

import com.egrand.wfw.auth.security.base.MyAbstractUserDetailsAuthenticationProvider;
import com.egrand.wfw.auth.security.base.MyAuthenticationToken;
import com.egrand.wfw.auth.security.model.MyUserDetail;
import java.util.Map;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class JinsanAuthenticationProvider
        extends MyAbstractUserDetailsAuthenticationProvider {
    private UserDetailsService userDetailsService;

    protected void additionalAuthenticationChecks(UserDetails var1, Authentication authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("PhoneAuthenticationProvider.badCredentials", "Bad credentials"));
        }
        Map<String, String> map = (Map<String, String>) authentication.getCredentials();
        String password = map.get("password");
        String identity = map.get("identity");
        String username = map.get("username");
        MyUserDetail myUserDetail = (MyUserDetail) var1;
        if (myUserDetail.getBaseUser().getAccountName().equals(username) && myUserDetail
                .getBaseUser().getIdentityId().equals(identity)) {
            this.logger.info("当前登录用户信息{}，密码{}，身份{}", new Object[]{username, password, identity});
        } else {
            this.logger.debug("Authentication failed: verifyCode does not match stored value");
            throw new BadCredentialsException(this.messages.getMessage("AuthenticationProvider.badCredentials", "Bad verifyCode"));
        }
    }

    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        MyAuthenticationToken result = new MyAuthenticationToken(principal, authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return (Authentication) result;
    }

    protected UserDetails retrieveUser(String username, Authentication authentication) throws AuthenticationException {
        UserDetails loadedUser;
        try {
            loadedUser = getUserDetailsService().loadUserByUsername(username);
        } catch (UsernameNotFoundException var6) {
            throw var6;
        } catch (Exception var7) {
            throw new InternalAuthenticationServiceException(var7.getMessage(), var7);
        }

        if (loadedUser == null) {
            throw new InternalAuthenticationServiceException("UserDetailsService returned null, which is an interface contract violation");
        }
        return loadedUser;
    }

    public boolean supports(Class<?> authentication) {
        return MyAuthenticationToken.class.isAssignableFrom(authentication);
    }

    public UserDetailsService getUserDetailsService() {
        return this.userDetailsService;
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
