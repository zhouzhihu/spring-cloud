package com.egrand.wfw.auth.security.model;

import com.egrand.wfw.auth.api.BaseRole;
import com.egrand.wfw.auth.api.BaseUser;
import java.util.ArrayList;
import java.util.List;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;

public class UserDetailFactory {
    public static MyUserDetail createUserDetail(BaseUser user, List<BaseRole> roles) {
        return new MyUserDetail(user, roles);
    }

    public static MyUserDetail createUserDetail(BaseUser user) {
        return new MyUserDetail(user, new ArrayList());
    }

    public static BaseClientDetails createClientDetails(BaseUser user) {
        BaseClientDetails details = new BaseClientDetails(user.getClientId(), user.getResourceIds(), user.getScope(), user.getGrantTypes(), "", user.getWebServerRedirectUri());
        details.setClientSecret(user.getClientSecret());
        return details;
    }
}