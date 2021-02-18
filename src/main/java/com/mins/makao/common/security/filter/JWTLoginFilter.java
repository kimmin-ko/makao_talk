package com.mins.makao.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mins.makao.common.security.Tokens;
import com.mins.makao.common.security.User;
import com.mins.makao.common.security.UserDetailsServiceImpl;
import com.mins.makao.common.security.UserLogin;
import com.mins.makao.common.util.CookieUtil;
import com.mins.makao.common.util.JWTUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JWTLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public JWTLoginFilter(JWTUtil jwtUtil, ObjectMapper objectMapper, UserDetailsServiceImpl userDetailsServiceImpl, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;
        this.userDetailsServiceImpl = userDetailsServiceImpl;

        setFilterProcessesUrl("/login");
    }

    @Override
    @SneakyThrows(IOException.class)
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals("POST")) {
            throw new AuthenticationServiceException(
                    "Authentication method not supported: " + request.getMethod());
        }

        UserLogin login = objectMapper.readValue(request.getInputStream(), UserLogin.class);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        User user = (User) authResult.getPrincipal();

        String accessToken = jwtUtil.generate(user.getId(), Tokens.Type.ACCESS);
        String refreshToken = jwtUtil.generate(user.getId(), Tokens.Type.REFRESH);

        userDetailsServiceImpl.saveRefreshToken(user.getId(), refreshToken);

        // TODO RefreshToken 을 LocalStorage 에 저장할 경우 XSS 에 대한 대비가 되어야함
        response.setHeader(JWTUtil.REFRESH_HEADER, refreshToken);

        //TODO AccessToken 을 Cookie 에 저장하여 인증에 사용할 경우 CSRF 에 대한 대비가 되어야함
        CookieUtil.create(response, "accessToken", accessToken, (int) jwtUtil.getProperties().getAccessTokenLifeTime());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request,
                                              HttpServletResponse response,
                                              AuthenticationException failed) throws IOException, ServletException {
        log.warn("Login Failed: ", failed);
        super.unsuccessfulAuthentication(request, response, failed);
    }
}