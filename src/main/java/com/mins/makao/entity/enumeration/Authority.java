package com.mins.makao.entity.enumeration;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

@Getter
public enum Authority implements GrantedAuthority {
    ROLE_USER("일반 회원"),
    ROLE_ADMIN("관리자");

    private final String comment;

    Authority(String comment) {
        this.comment = comment;
    }

    @Override
    public String getAuthority() {
        return this.name();
    }
}
