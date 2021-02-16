package com.mins.makao.common.config;

import com.mins.makao.common.interceptor.APILoggingInterceptor;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

import static java.time.Duration.ofSeconds;

@Configuration
public class AppConfig {

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .requestFactory(() -> new BufferingClientHttpRequestFactory(new SimpleClientHttpRequestFactory()))
                .setReadTimeout(ofSeconds(5))
                .setConnectTimeout(ofSeconds(5))
                .additionalMessageConverters(new StringHttpMessageConverter(StandardCharsets.UTF_8))
                .additionalInterceptors(new APILoggingInterceptor())
                .build();
    }

}
