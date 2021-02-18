package com.mins.makao.common.security.filter;

import com.mins.makao.common.security.UserDetailsServiceImpl;
import com.mins.makao.common.util.JWTUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTCheckFilter extends BasicAuthenticationFilter {

    private final JWTUtil jwtUtil;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    public JWTCheckFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, UserDetailsServiceImpl userDetailsServiceImpl) {
        super(authenticationManager);
        this.jwtUtil = jwtUtil;
        this.userDetailsServiceImpl = userDetailsServiceImpl;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {


        chain.doFilter(request, response);
    }

}
