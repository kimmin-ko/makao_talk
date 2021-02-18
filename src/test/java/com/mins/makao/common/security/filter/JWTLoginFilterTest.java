package com.mins.makao.common.security.filter;

import com.mins.makao.common.security.UserLogin;
import com.mins.makao.domain.member.repository.MemberRepository;
import com.mins.makao.entity.Member;
import com.mins.makao.entity.MemberTestHelper;
import com.mins.makao.entity.enumeration.Authority;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JWTLoginFilterTest {

    @LocalServerPort
    int port;

    @Autowired
    TestRestTemplate restTemplate;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    MemberTestHelper memberTestHelper;

    Member member;

    private URI uri(String path) throws URISyntaxException {
        return new URI(String.format("http://localhost:%d%s", port, path));
    }

    @BeforeEach
    void beforeEach() {
        memberTestHelper = new MemberTestHelper(memberRepository, passwordEncoder);

        member = memberTestHelper.createMember("user1", Authority.ROLE_USER);
    }

    @AfterEach
    void afterEach() {
        memberTestHelper.clearMember(member);
    }

    @Test
    @DisplayName("1. JWT 로 로그인을 시도한다.")
    void jwt_login() throws URISyntaxException {
        // given
        String email = "user1@test.com";
        UserLogin user1 = UserLogin.builder()
                .username(email)
                .password("user19999")
                .build();

        HttpEntity<UserLogin> entity = new HttpEntity<>(user1);

        // when
        ResponseEntity<String> response = restTemplate.exchange(uri("/login"), HttpMethod.POST, entity, String.class);

        String refreshToken = response.getHeaders().get("refresh-token").get(0);
        String cookies = response.getHeaders().get("Set-Cookie").get(0);

        Member findMember = memberRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("회원을 찾을 수 없습니다. : " + email));

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
        assertThat(cookies).contains("accessToken");
        assertThat(findMember.getAuthToken().getRefreshToken()).isEqualTo(refreshToken);
    }

    @Test
    @DisplayName("2. 비밀번호가 틀리면 로그인하지 못한다.")
    void jwt_fail_login_() throws URISyntaxException {
        // given
        UserLogin login = UserLogin.builder()
                .username("user1@test.com")
                .password("user1")
                .build();

        HttpEntity<UserLogin> entity = new HttpEntity<>(login);

        // then
        assertThatThrownBy(() -> restTemplate.exchange(uri("/login"), HttpMethod.POST, entity, String.class))
                .isInstanceOf(ResourceAccessException.class);
    }

}
