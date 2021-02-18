package com.mins.makao.domain.member.service;

import com.mins.makao.common.exception.business.DuplicateAccountException;
import com.mins.makao.common.exception.business.DuplicateEmailException;
import com.mins.makao.domain.member.repository.MemberRepository;
import com.mins.makao.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static java.lang.String.*;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final PasswordEncoder passwordEncoder;
    private final MemberRepository memberRepository;

    @Transactional
    public Long join(Member member) {
        checkDuplicationEmail(member.getEmail());
        checkDuplicationAccount(member.getAccount());

        member.encryptPassword(passwordEncoder);

        return memberRepository.save(member).getId();
    }

    public void checkDuplicationEmail(String email) {
        boolean exists = memberRepository.existsByEmail(email);

        if (exists)
            throw new DuplicateEmailException(format("이메일이 이미 존재합니다. : %s", email));
    }

    public void checkDuplicationAccount(String account) {
        boolean exists = memberRepository.existsByAccount(account);

        if (exists)
            throw new DuplicateAccountException(format("계정이 이미 존재합니다. : %s", account));
    }

}