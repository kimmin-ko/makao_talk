package com.mins.makao;

import com.mins.makao.common.properties.JWTProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@EnableConfigurationProperties({JWTProperties.class})
@SpringBootApplication
public class MakaoTalkApplication {

    public static void main(String[] args) {
        SpringApplication.run(MakaoTalkApplication.class, args);
    }

}
