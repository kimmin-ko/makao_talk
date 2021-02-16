package com.mins.makao.entity;

import com.mins.makao.entity.base.BaseTimeEntity;
import com.mins.makao.entity.enumeration.Authority;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Getter
@Entity
@ToString
@NoArgsConstructor(access = PROTECTED)
public class Member extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String account;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    private String phoneNumber;

    private String stateMessage;

    private String profileUrl;

    private LocalDate birthDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 10)
    private Authority authority;

    private LocalDateTime withdrawalDate;

    public Member(String account, String email, String password, String name, LocalDate birthDate) {
        this.account = account;
        this.email = email;
        this.password = password;
        this.name = name;
        this.birthDate = birthDate;
    }

    @Builder
    private Member(String account, String email, String password, String name, String phoneNumber, LocalDate birthDate, Authority authority) {
        this.account = account;
        this.email = email;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.birthDate = birthDate;
        this.authority = authority;
    }

    /* 생성 메서드 */

    /* 연관관계 메서드 */

    /* 비지니스 로직 */

    /* 조회 로직 */

}