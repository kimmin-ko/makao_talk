package com.mins.makao.entity;

import com.mins.makao.entity.base.BaseTimeEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AuthToken extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="member_id", unique = true, nullable = false)
    private Member member;

    @Column(name = "refresh_token", unique = true, nullable = false, length = 500)
    private String refreshToken;

    private AuthToken(Member member, String refreshToken) {
        this.member = member;
        this.refreshToken = refreshToken;
    }

    /* 팩토리 메서드 */
    public static AuthToken create(Member member, String refreshToken) {
        return new AuthToken(member, refreshToken);
    }

    /* 연관관계 편의 메서드 */
    public void setMember(Member member) {
        this.member = member;
    }

}
