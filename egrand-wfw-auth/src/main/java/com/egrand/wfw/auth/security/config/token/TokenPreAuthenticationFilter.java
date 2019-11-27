package com.egrand.wfw.auth.security.config.token;

import com.egrand.wfw.auth.security.config.token.TokenConfigUtil;
import javax.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

public class TokenPreAuthenticationFilter
        extends AbstractPreAuthenticatedProcessingFilter {
    public TokenPreAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
    }

    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        return request.getHeader(TokenConfigUtil.SSO_TOKEN);
    }

    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        return TokenConfigUtil.SSO_CREDENTIALS;
    }
}
