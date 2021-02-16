package com.mins.makao.common.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mins.makao.common.security.Tokens;
import com.mins.makao.common.security.User;
import com.mins.makao.common.security.UserLogin;
import com.mins.makao.common.util.JWTUtil;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
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

    public JWTLoginFilter(JWTUtil jwtUtil, ObjectMapper objectMapper, AuthenticationManager authenticationManager) {
        this.jwtUtil = jwtUtil;
        this.objectMapper = objectMapper;
        this.authenticationManager = authenticationManager;

        setFilterProcessesUrl("/login");
    }

    @Override
    @SneakyThrows(IOException.class)
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        UserLogin login = objectMapper.readValue(request.getInputStream(), UserLogin.class);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("authResult: {}", authResult);
        User user = (User) authResult.getPrincipal();
        String token = jwtUtil.generate(user.getId(), Tokens.Type.ACCESS);
        response.setHeader(JWTUtil.AUTH_HEADER, JWTUtil.BEARER + token);
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.warn("Login Failed: ", failed);
        super.unsuccessfulAuthentication(request, response, failed);
    }
}
