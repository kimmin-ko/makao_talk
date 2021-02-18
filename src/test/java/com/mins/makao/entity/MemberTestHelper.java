package com.mins.makao.entity;

import com.mins.makao.domain.member.repository.MemberRepository;
import com.mins.makao.entity.enumeration.Authority;
import org.springframework.security.crypto.password.PasswordEncoder;


public class MemberTestHelper {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberTestHelper(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Member createMember(String name, Authority authority) {
        Member member = Member.builder()
                .email(name + "@test.com")
                .password(passwordEncoder.encode(name + "9999"))
                .name(name)
                .account(name + "account")
                .authority(authority)
                .build();

        return memberRepository.save(member);
    }

    public void clearMember(Member member) {
        memberRepository.delete(member);
    }

    public void clearMember() {
        memberRepository.deleteAll();
    }

}