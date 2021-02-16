package com.mins.makao.entity.enumeration;

import lombok.Getter;

public enum FriendStatus {
    NORMAL("일반"),
    FAVORITES("즐겨찾기"),
    HIDDEN("숨김"),
    BLOCK("차단");

    @Getter
    private final String comment;

    FriendStatus(String comment) {
        this.comment = comment;
    }
}
