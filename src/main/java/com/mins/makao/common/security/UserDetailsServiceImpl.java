package com.mins.makao.common.security;

import com.mins.makao.domain.member.repository.MemberRepository;
import com.mins.makao.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

import static java.lang.String.format;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("회원을 찾을 수 없습니다. username: %s", username)));

        return User.builder()
                .id(member.getId())
                .email(member.getEmail())
                .password(member.getPassword())
                .authorities(Collections.singleton(member.getAuthority()))
                .enabled(true)
                .build();
    }

}
