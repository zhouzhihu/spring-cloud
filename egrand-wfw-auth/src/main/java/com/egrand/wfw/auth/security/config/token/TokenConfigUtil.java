package com.egrand.wfw.auth.security.config.token;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenConfigUtil
{
    public static String SSO_TOKEN;
    public static String SSO_CREDENTIALS;

    @Value("${sso-token-key:Authorization}")
    public void setSsoToken(String ssoToken) { SSO_TOKEN = ssoToken; }

    @Value("${sso-credentials:N/A}")
    public void setSsoCredentials(String ssoCredentials) { SSO_CREDENTIALS = ssoCredentials; }
}
