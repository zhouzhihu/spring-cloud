package com.egrand.wfw.auth.security;

import com.egrand.wfw.auth.security.service.BaseRoleService;
import com.egrand.commons.lang.StringUtils;
import java.util.Collection;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Service;

@Service("roleBasedVoter")
public class RoleBasedVoter
        implements AccessDecisionVoter<Object>
{
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private BaseRoleService roleService;

    public boolean supports(ConfigAttribute attribute) { return true; }

    public boolean supports(Class<?> clazz) { return true; }

    public int vote(Authentication authentication, Object object, Collection<ConfigAttribute> attributes) {
        HttpServletRequest request = ((FilterInvocation)object).getHttpRequest();
        if (authentication == null)
        {
            return -1;
        }
        int result = 0;

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (ConfigAttribute attribute : attributes) {
            if (attribute.getAttribute() == null) {
                continue;
            }
            if (supports(attribute)) {
                result = -1;

                for (GrantedAuthority authority : authorities) {
                    if (attribute.getAttribute().equals(authority.getAuthority()))
                    {
                        return 1;
                    }
                }
            }
        }
        String url = request.getRequestURI();
        String httpMethod = request.getMethod();
        String roleCode = this.roleService.getRoles(url, httpMethod);
        if (StringUtils.isNoneEmpty(new CharSequence[] { roleCode })) {
            result = -1;
            for (GrantedAuthority authority : authorities) {
                if (roleCode.equals(authority.getAuthority()))
                {
                    return 1;
                }
            }
        }
        return result;
    }
}