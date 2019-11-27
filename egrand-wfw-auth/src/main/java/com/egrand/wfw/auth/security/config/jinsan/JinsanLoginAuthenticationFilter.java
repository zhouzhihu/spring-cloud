package com.egrand.wfw.auth.security.config.jinsan;

import com.egrand.wfw.auth.security.base.MyAuthenticationToken;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

public class JinsanLoginAuthenticationFilter
        extends AbstractAuthenticationProcessingFilter {
    public JinsanLoginAuthenticationFilter() {
        super((RequestMatcher) new AntPathRequestMatcher("/authentication/jinsan", "POST"));
    }

    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request
                    .getMethod());
        }
        Map<Object, Object> map = new HashMap<>();
        String identity = obtainParameter(request, "identity");
        map.put("username", obtainParameter(request, "username"));
        map.put("password", obtainParameter(request, "password"));
        map.put("identity", identity);
        String principal = identity.trim();
        MyAuthenticationToken myAuthenticationToken = new MyAuthenticationToken(principal, map);
        setDetails(request, (AbstractAuthenticationToken) myAuthenticationToken);
        return getAuthenticationManager().authenticate((Authentication) myAuthenticationToken);
    }

    private void setDetails(HttpServletRequest request, AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    private String obtainParameter(HttpServletRequest request, String parameter) {
        String result = request.getParameter(parameter);
        return (result == null) ? "" : result;
    }
}
