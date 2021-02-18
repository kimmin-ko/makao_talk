package com.mins.makao.common.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mins.makao.common.security.AuthenticationEntryPointImpl;
import com.mins.makao.common.security.UserDetailsServiceImpl;
import com.mins.makao.common.security.filter.JWTLoginFilter;
import com.mins.makao.common.util.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper;
    private final UserDetailsServiceImpl userDetailsServiceImpl;
    private final AuthenticationEntryPointImpl authenticationEntryPointImpl;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsServiceImpl)
                .passwordEncoder(passwordEncoder());
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/static/**");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        JWTLoginFilter loginFilter = new JWTLoginFilter(jwtUtil, objectMapper, authenticationManager());

        http
                .csrf().disable()
                .addFilter(loginFilter)
                .authorizeRequests(config ->
                        config
                                .antMatchers("/error").permitAll()
                                .antMatchers("/favicon.ico").permitAll()
                                .antMatchers("/join").permitAll()
                                .antMatchers("/loginForm").permitAll()
                                .anyRequest().authenticated()
                )
                .exceptionHandling(config ->
                        config
                                .authenticationEntryPoint(authenticationEntryPointImpl)
                );

    }

}