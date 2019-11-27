package com.egrand.wfw.auth.security.model;

import com.egrand.wfw.auth.api.BaseRole;
import com.egrand.wfw.auth.api.BaseUser;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class MyUserDetail implements UserDetails, Serializable {
    private BaseUser baseUser;
    private List<BaseRole> roles;

    public MyUserDetail() {
    }

    public MyUserDetail(BaseUser baseUser, List<BaseRole> roles) {
        this.baseUser = baseUser;
        this.roles = roles;
    }

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return (Collection) this.roles;
    }

    public String getPassword() {
        return this.baseUser.getPassword();
    }

    public String getUsername() {
        return this.baseUser.getAccountName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return true;
    }

    public BaseUser getBaseUser() {
        return this.baseUser;
    }

    public void setBaseUser(BaseUser baseUser) {
        this.baseUser = baseUser;
    }

    public List<BaseRole> getRoles() {
        return this.roles;
    }

    public void setRoles(List<BaseRole> roles) {
        this.roles = roles;
    }
}
