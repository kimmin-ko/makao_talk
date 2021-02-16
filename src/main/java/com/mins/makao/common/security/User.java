package com.mins.makao.common.security;

import com.mins.makao.entity.enumeration.Authority;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Set;

@Getter
@Builder
public class User implements UserDetails {

    private final Long id;
    private final String email;
    private final String password;
    private final Set<Authority> authorities;
    private final boolean enabled;

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

}
