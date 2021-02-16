package com.mins.makao.common.security.filter;

import com.mins.makao.common.security.UserLogin;
import com.mins.makao.common.util.JWTUtil;
import com.mins.makao.domain.member.repository.MemberRepository;
import com.mins.makao.entity.MemberTestHelper;
import com.mins.makao.entity.enumeration.Authority;
import org.assertj.core.api.Assertions;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.net.URI;
import java.net.URISyntaxException;

import static org.assertj.core.api.Assertions.*;

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

    private URI uri(String path) throws URISyntaxException {
        return new URI(String.format("http://localhost:%d%s", port, path));
    }

    @BeforeEach
    void beforeEach() {
        memberTestHelper = new MemberTestHelper(memberRepository, passwordEncoder);

        memberTestHelper.clearMember();
        memberTestHelper.createMember("user1", Authority.ROLE_USER);
    }

    @Test
    @DisplayName("1. JWT 로 로그인을 시도한다.")
    void jwt_login_test() throws URISyntaxException {
        // given
        UserLogin user1 = UserLogin.builder()
                .username("user1@test.com")
                .password("user19999")
                .build();

        HttpEntity<UserLogin> entity = new HttpEntity<>(user1);

        // when
        ResponseEntity<String> response = restTemplate.exchange(uri("/login"), HttpMethod.POST, entity, String.class);

        // then
        assertThat(response.getStatusCodeValue()).isEqualTo(200);
    }

}